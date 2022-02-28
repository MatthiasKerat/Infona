package kerasinoapps.kapps.infona.auth_module.domain.use_case

import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.RegisterRequest
import kerasinoapps.kapps.infona.auth_module.domain.repository.AuthRepository
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.data.local.preferences.UserPreferences
import kerasinoapps.kapps.infona.data.remote.interceptor.TokenInterceptor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences,
    private val tokenInterceptor: TokenInterceptor
) {

    operator fun invoke(registerRequest: RegisterRequest): Flow<Resource<Boolean>> = flow {
        try{
            emit(Resource.Loading())
            val response = authRepository.register(registerRequest)
            val token = response.token

            if(token==null || !response.successful){
                emit(Resource.Error(message = response.message))
                return@flow
            }

            userPreferences.saveToken(token)
            tokenInterceptor.saveToken(token)

            emit(Resource.Success(true))
        }catch(e: HttpException){
            emit(Resource.Error<Boolean>(e.localizedMessage ?: "An unexpected error occured"))
        }catch(e: IOException) {
            emit(Resource.Error<Boolean>("Couldn't reach server. Check your internet connection."))
        }
    }

}