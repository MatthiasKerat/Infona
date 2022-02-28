package kerasinoapps.kapps.infona.auth_module.presentation.state

data class SendVerificationCodeState(
    val successful:Boolean = false,
    val error:String? = null,
    val loading:Boolean = false
)
