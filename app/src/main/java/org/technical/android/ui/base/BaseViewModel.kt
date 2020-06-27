package org.technical.android.ui.base

import android.net.NetworkInfo
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.technical.android.di.data.DataManager
import org.technical.android.di.data.network.responseAdapter.MaybeResponseAdapter
import org.technical.android.di.data.network.responseAdapter.ResponseAdapter
import org.technical.android.di.data.network.utils.RxDefaultRetryHandler
import org.technical.android.util.liveData.SingleLiveData
import org.technical.android.util.progresshandler.ProgressHandler
import retrofit2.adapter.rxjava2.Result


open class BaseViewModel(
    val mDataManager: DataManager,
    private val mCompositeDisposable: CompositeDisposable
) : ViewModel() {

    var disposable: HashMap<Int, Disposable?> = HashMap()

    var errorLiveData = SingleLiveData<Throwable>()
    var isCallingApi = false

    fun <MODEL> makeRequest(
        single: Single<Result<MODEL>>,
        progressHandler: ProgressHandler? = null
    ): Single<MODEL> {
        isCallingApi = true
        progressHandler?.showProgress()
        return single.flatMap(ResponseAdapter())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(RxDefaultRetryHandler(3))
            .doOnError(errorLiveData::setValue)
            .doFinally {
                progressHandler?.dismissProgress()
                isCallingApi = false
            }
    }

    fun <MODEL> makeCompletableRequest(
        single: Single<Result<MODEL>>,
        progressHandler: ProgressHandler? = null
    ): Completable {
        isCallingApi = true
        progressHandler?.showProgress()
        return  single.flatMapCompletable(MaybeResponseAdapter())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(RxDefaultRetryHandler(3))
            .doOnError(errorLiveData::setValue)
            .doFinally {
                progressHandler?.dismissProgress()
                isCallingApi = false
            }
    }

    fun addDisposable(disposable: Disposable?) {
        disposable?.let {
            this.mCompositeDisposable.add(it)
        }
    }

    override fun onCleared() {
        this.mCompositeDisposable.dispose()
        this.mCompositeDisposable.clear()
        super.onCleared()
    }
}
