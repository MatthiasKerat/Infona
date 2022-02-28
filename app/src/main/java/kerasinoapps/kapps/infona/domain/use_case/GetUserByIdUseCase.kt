package kerasinoapps.kapps.infona.domain.use_case

import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(id:String): User {
        return userRepository.getUserById(id)
    }

}