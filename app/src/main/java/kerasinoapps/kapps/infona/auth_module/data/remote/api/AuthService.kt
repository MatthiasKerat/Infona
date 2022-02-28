package kerasinoapps.kapps.infona.auth_module.data.remote.api

import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.LoginRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.RegisterRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.ResetPasswordRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.ValidateEmailRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.ForgotPasswordResponse
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.LoginResponse
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.RegisterResponse
import kerasinoapps.kapps.infona.data.remote.dto.response.DefaultResponse
import retrofit2.http.*

interface AuthService {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ):LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ):RegisterResponse

    @GET("auth/check_user/{email}")
    suspend fun check_Email(
        @Path ("email") email:String
    ):ForgotPasswordResponse

    @POST("auth/reset_password")
    suspend fun reset_Password(
        @Body resetPasswordRequest: ResetPasswordRequest
    ): DefaultResponse

    @POST("auth/validate_email")
    suspend fun validate_Email(
        @Body validateEmailRequest: ValidateEmailRequest
    ):DefaultResponse

    @GET("auth/request_verification_code/{email}")
    suspend fun request_verification_code(
        @Path("email")email:String
    ):DefaultResponse

}