package kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response

data class RegisterResponse(
    val successful: Boolean,
    val message:String,
    val token:String? = null
)
