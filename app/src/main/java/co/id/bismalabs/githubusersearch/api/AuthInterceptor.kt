package co.id.bismalabs.githubusersearch.api

import co.id.bismalabs.githubusersearch.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "token ${Constants.TOKEN}")

        return chain.proceed(requestBuilder.build())
    }

}