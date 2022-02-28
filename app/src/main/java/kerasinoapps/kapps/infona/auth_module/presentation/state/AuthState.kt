package kerasinoapps.kapps.infona.auth_module.presentation.state

data class AuthState(
    val successful:Boolean = false,
    val errorMessage:String? = null,
    val inputError:Boolean = true,
    val inputErrorType: AuthInputErrorType = AuthInputErrorType.EmptyField,
    val loading: Boolean = false
)