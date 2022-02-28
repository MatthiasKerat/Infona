package kerasinoapps.kapps.infona.auth_module.domain.use_case

import kerasinoapps.kapps.infona.auth_module.presentation.state.AuthInputErrorType
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class CheckRegisterInputUseCase @Inject constructor(

) {
    operator fun invoke(email: String, password: String, passwordRepeat: String,considerEmail:Boolean = true): AuthInputErrorType {
        if(email.isBlank()||password.isBlank()||passwordRepeat.isBlank()){
            return AuthInputErrorType.EmptyField
        }
        if(password.length<8){
            return AuthInputErrorType.PasswordToShort
        }
        if(password != passwordRepeat){
            return AuthInputErrorType.PasswordsDoNotMatch
        }
        if(password.filter { it.isDigit() }.length <= 0){
            return AuthInputErrorType.PasswordNoNumber
        }
        var p: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        var m: Matcher = p.matcher(password)
        var b: Boolean = m.find()
        if(!b){
            return AuthInputErrorType.PasswordNoSpecialCharakter
        }

        if(considerEmail){
            var p1: Pattern = Pattern.compile("[@ ]", Pattern.CASE_INSENSITIVE)
            var m1: Matcher = p1.matcher(email)
            var b1: Boolean = m1.find()
            if(!b1){
                return AuthInputErrorType.EmailDoesntContainAtSymbol
            }
        }

        return AuthInputErrorType.EverythingFine

    }
}