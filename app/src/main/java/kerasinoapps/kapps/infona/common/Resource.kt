package kerasinoapps.kapps.infona.common

sealed class Resource<out T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T, message:String? = null) : Resource<T>(data, message)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}