package org.technical.android.di.data.network.responseAdapter


import io.reactivex.Completable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import org.technical.android.di.data.network.utils.HttpException
import retrofit2.adapter.rxjava2.Result
import java.io.IOException

class MaybeResponseAdapter<T> : Function<Result<T>, Completable> {

    @Throws(IOException::class)
    override fun apply(@NonNull result: Result<T>): Completable {
        return if (result.isError) {
            Completable.error(result.error())
        } else {
            val response = result.response()
            if (response?.isSuccessful == true) {
                Completable.complete()
            } else {

                val httpException = HttpException(
                    response?.code(),
                    response?.message()
                )
                Completable.error(
                    httpException
                )
            }
        }
    }
}
