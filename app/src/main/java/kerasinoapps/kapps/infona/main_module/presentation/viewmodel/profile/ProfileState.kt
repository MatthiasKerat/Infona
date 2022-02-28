package kerasinoapps.kapps.infona.main_module.presentation.viewmodel.profile

data class ProfileState(
    val showData:Boolean = false,
    val information:List<ExerciseInfoElem> = listOf()
)
