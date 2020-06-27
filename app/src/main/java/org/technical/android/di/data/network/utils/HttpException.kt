package org.technical.android.di.data.network.utils

class HttpException(val statusCode: Int?, override val message: String?) : Exception()