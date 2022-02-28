package kerasinoapps.kapps.infona.auth_module.domain.use_case

import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.ValidateEmailRequest
import kerasinoapps.kapps.infona.auth_module.domain.repository.AuthRepository
import kerasinoapps.kapps.infona.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(validateEmailRequest: ValidateEmailRequest): Flow<Resource<Boolean>> = flow {
        try{
            emit(Resource.Loading())
            val response = authRepository.validate_Email(validateEmailRequest)
            emit(Resource.Success(response.successful,message = response.error_message))
        }catch(e: HttpException){
            emit(Resource.Error<Boolean>(e.localizedMessage ?: "An unexpected error occured"))
        }catch(e: IOException) {
            emit(Resource.Error<Boolean>("Couldn't reach server. Check your internet connection."))
        }
    }

}