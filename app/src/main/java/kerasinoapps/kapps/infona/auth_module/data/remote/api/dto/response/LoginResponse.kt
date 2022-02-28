package kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response

import kerasinoapps.kapps.infona.domain.model.User

data class LoginResponse(
    val successful: Boolean,
    val message:String,
    val validated: Boolean,
    val user:User,
    val token:String? = null,
)
