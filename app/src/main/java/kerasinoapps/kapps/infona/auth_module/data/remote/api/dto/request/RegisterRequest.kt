package kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request

import kerasinoapps.kapps.infona.domain.model.User

data class RegisterRequest(
    val user: User,
    val password:String
)
