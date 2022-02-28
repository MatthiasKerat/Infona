package kerasinoapps.kapps.infona.auth_module.domain.repository

import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.LoginRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.RegisterRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.ResetPasswordRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.ValidateEmailRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.ForgotPasswordResponse
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.LoginResponse
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.RegisterResponse
import kerasinoapps.kapps.infona.data.remote.dto.response.DefaultResponse

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest):LoginResponse

    suspend fun register(registerRequest: RegisterRequest):RegisterResponse

    suspend fun check_Email(email:String):ForgotPasswordResponse

    suspend fun reset_Password(resetPasswordRequest: ResetPasswordRequest): DefaultResponse

    suspend fun validate_Email(validateEmailRequest: ValidateEmailRequest):DefaultResponse

    suspend fun request_Verification_Code(email:String):DefaultResponse

}