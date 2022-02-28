package kerasinoapps.kapps.infona.main_module.presentation.viewmodel.profile

data class UploadImageState(
    val successful:Boolean = false,
    val error:String? = null,
    val loading:Boolean = false
)
