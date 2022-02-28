package kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request

data class ResetPasswordRequest (
    val email:String,
    val code:String,
    val password:String
)