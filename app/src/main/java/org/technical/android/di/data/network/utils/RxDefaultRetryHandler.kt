package org.technical.android.di.data.network.utils

import io.reactivex.functions.BiPredicate
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class RxDefaultRetryHandler(private val mInt: Int) : BiPredicate<Int, Throwable> {

    override fun test(t1: Int, t2: Throwable): Boolean {
        return t1 < mInt && checkException(
            t2
        )
    }

    companion object {


        fun checkException(t2: Throwable): Boolean {
            return (t2 is SocketTimeoutException ||
                    t2 is TimeoutException ||
                    t2 is ConnectException ||
                    t2 is UnknownHostException)
        }
    }
}
