package kerasinoapps.kapps.infona.auth_module.presentation.state

data class ForgotPasswordState(
    val successful:Boolean = false,
    val userDoesNotExist:Boolean = false,
    val loading:Boolean = false,
    val error:String? = null
)
