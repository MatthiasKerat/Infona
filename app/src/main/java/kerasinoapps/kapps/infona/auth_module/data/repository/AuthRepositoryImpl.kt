package kerasinoapps.kapps.infona.auth_module.data.repository

import kerasinoapps.kapps.infona.auth_module.data.remote.api.AuthService
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.LoginRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.RegisterRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.ResetPasswordRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.ValidateEmailRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.ForgotPasswordResponse
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.LoginResponse
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.RegisterResponse
import kerasinoapps.kapps.infona.data.remote.dto.response.DefaultResponse
import kerasinoapps.kapps.infona.auth_module.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return authService.login(loginRequest)
    }

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse {
        return authService.register(registerRequest)
    }

    override suspend fun check_Email(email: String): ForgotPasswordResponse {
        return authService.check_Email(email)
    }

    override suspend fun reset_Password(resetPasswordRequest: ResetPasswordRequest): DefaultResponse {
        return authService.reset_Password(resetPasswordRequest)
    }

    override suspend fun validate_Email(validateEmailRequest: ValidateEmailRequest): DefaultResponse {
        return authService.validate_Email(validateEmailRequest)
    }

    override suspend fun request_Verification_Code(email: String): DefaultResponse {
        return authService.request_verification_code(email)
    }
}