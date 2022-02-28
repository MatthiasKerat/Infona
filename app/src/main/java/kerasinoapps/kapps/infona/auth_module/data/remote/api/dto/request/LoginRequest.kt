package kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request

data class LoginRequest(
    val email:String,
    val password:String,
    val stay_logged_in:Boolean
)
