package kerasinoapps.kapps.infona.auth_module.domain.use_case

import kerasinoapps.kapps.infona.auth_module.presentation.state.AuthInputErrorType
import javax.inject.Inject

class CheckLoginInputUseCase @Inject constructor(

) {
    operator fun invoke(email:String,password:String): AuthInputErrorType {
        if(email.isBlank() || password.isBlank()){
            return AuthInputErrorType.EmptyField
        }
        return AuthInputErrorType.EverythingFine
    }
}