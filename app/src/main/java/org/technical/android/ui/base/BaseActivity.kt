package org.technical.android.ui.base

import android.app.Activity
import android.content.Intent
import android.net.NetworkInfo
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : DaggerAppCompatActivity(){

    private var disposable: Disposable? = null

    lateinit var binding: T

    open var isHandleNetworkConnectivity = true

    open var viewModel: V? = null
        set(value) {
            field = value
            field?.errorLiveData?.observe(this, Observer { error ->

            })
        }

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutId())

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
