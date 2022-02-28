package kerasinoapps.kapps.infona.data.remote.interceptor

import okhttp3.Interceptor

interface TokenInterceptor : Interceptor {

    fun saveToken(token:String)

}