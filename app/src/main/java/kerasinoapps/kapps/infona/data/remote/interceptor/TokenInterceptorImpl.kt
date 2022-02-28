package kerasinoapps.kapps.infona.data.remote.interceptor

import kerasinoapps.kapps.infona.common.Constants
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptorImpl : TokenInterceptor {

    var token: String? = null

    override fun saveToken(token: String) {
        this.token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if(request.url.encodedPath in Constants.IGNORE_AUTH_URLS){
            return chain.proceed(request)
        }
        val authenticatetRequest = request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(authenticatetRequest)
    }

}