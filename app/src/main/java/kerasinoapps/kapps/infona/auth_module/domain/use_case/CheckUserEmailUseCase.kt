package kerasinoapps.kapps.infona.auth_module.domain.use_case

import kerasinoapps.kapps.infona.auth_module.domain.repository.AuthRepository
import kerasinoapps.kapps.infona.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CheckUserEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(email:String) : Flow<Resource<Boolean>> = flow {
        try{
            emit(Resource.Loading())
            val response = authRepository.check_Email(email)

            emit(Resource.Success(response.exists))
        }catch(e: HttpException){
            emit(Resource.Error<Boolean>(e.localizedMessage ?: "An unexpected error occured"))
        }catch(e: IOException) {
            emit(Resource.Error<Boolean>("Couldn't reach server. Check your internet connection."))
        }
    }

}