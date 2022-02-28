package kerasinoapps.kapps.infona.domain.use_case

import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.LoginResponse
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.domain.repository.UserRepository
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<Resource<List<User>>> = flow {

        try{
            emit(Resource.Loading())
            val response = userRepository.getUsers()

            emit(Resource.Success(response))
        }catch(e: HttpException){
            emit(Resource.Error<List<User>>(e.localizedMessage ?: "An unexpected error occured"))
        }catch(e: IOException) {
            emit(Resource.Error<List<User>>("Couldn't reach server. Check your internet connection."))
        }
    }

}