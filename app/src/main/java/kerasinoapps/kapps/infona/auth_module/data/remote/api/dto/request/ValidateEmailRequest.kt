package kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request

data class ValidateEmailRequest(
    val email:String,
    val code:String
)
