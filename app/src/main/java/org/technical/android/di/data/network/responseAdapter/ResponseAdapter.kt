package org.technical.android.di.data.network.responseAdapter


import io.reactivex.Single
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import org.technical.android.di.data.network.utils.HttpException
import retrofit2.adapter.rxjava2.Result
import java.io.IOException


class ResponseAdapter<T> : Function<Result<T>, Single<T>> {

    @Throws(IOException::class)
    override fun apply(@NonNull result: Result<T>): Single<T> {
        return if (result.isError) {
            Single.error(result.error())
        } else {
            val response = result.response()
            val parse = response?.body()
            if (response?.isSuccessful==true && parse != null) {
                    Single.just(parse)
            } else {
                val httpException = HttpException(
                    response?.code(),
                    response?.message()
                )
                Single.error(
                    httpException
                )
            }
        }
    }
}
