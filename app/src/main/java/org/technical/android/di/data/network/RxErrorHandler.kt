package org.technical.android.di.data.network


import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import io.reactivex.functions.Consumer
import org.technical.android.di.data.network.utils.HttpException
import java.nio.charset.Charset


class RxErrorHandler(internal var view: View) : Consumer<Throwable> {


    private fun isValidISOLatin1(s: String): Boolean {
        return Charset.forName("US-ASCII").newEncoder().canEncode(s)
    }

    @Throws(Exception::class)
    override fun accept(throwable: Throwable) {
        when {
            throwable is HttpException -> {

                if (throwable.statusCode == 401) {
                    // TODO: logout user or refresh token
                } else {
                    // TODO: show error message
                }
            }
            throwable is NullPointerException -> {
                throwable.message?.let {
                    // TODO: show error message
                }
            }
            throwable.message != null -> {
                throwable.message?.let {
                    val string = if (isValidISOLatin1(it)) {
                        "An error has occurred"
                    } else {
                        it
                    }
                    // TODO: show error message
                }
            }
        }
        throwable.printStackTrace()
    }
}

