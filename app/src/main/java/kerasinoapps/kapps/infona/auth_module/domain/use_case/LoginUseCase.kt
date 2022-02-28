package kerasinoapps.kapps.infona.auth_module.domain.use_case

import android.util.Log
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.LoginRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.LoginResponse
import kerasinoapps.kapps.infona.auth_module.domain.repository.AuthRepository
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.data.local.preferences.UserPreferences
import kerasinoapps.kapps.infona.data.remote.interceptor.TokenInterceptor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences,
    private val tokenInterceptor: TokenInterceptor
) {

    operator fun invoke(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> = flow {

        try{
            emit(Resource.Loading())
            val response = authRepository.login(loginRequest)
            val token = response.token

            if(token==null || !response.successful){
                emit(Resource.Error(message = response.message))
                return@flow
            }

            userPreferences.saveToken(token)
            tokenInterceptor.saveToken(token)

            emit(Resource.Success(response))
        }catch(e: HttpException){
            emit(Resource.Error<LoginResponse>(e.localizedMessage ?: "An unexpected error occured"))
        }catch(e: IOException) {
            emit(Resource.Error<LoginResponse>("Couldn't reach server. Check your internet connection."))
        }
    }

}