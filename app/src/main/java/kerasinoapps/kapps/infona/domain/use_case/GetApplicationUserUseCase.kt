package kerasinoapps.kapps.infona.domain.use_case

import kerasinoapps.kapps.infona.domain.repository.UserRepository
import javax.inject.Inject

class GetApplicationUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() = userRepository.getApplicationUser()

}