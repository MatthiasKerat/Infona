package kerasinoapps.kapps.infona.auth_module.presentation.state

data class ValidateEmailState(
    val successful:Boolean = false,
    val error:String? = null,
    val buttonEnabled:Boolean = false,
    val loading:Boolean = false
)