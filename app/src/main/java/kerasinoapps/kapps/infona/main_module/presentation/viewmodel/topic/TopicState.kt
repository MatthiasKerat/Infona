package kerasinoapps.kapps.infona.main_module.presentation.viewmodel.topic

import kerasinoapps.kapps.infona.main_module.presentation.viewmodel.profile.ExerciseInfoElem

data class TopicState(
    val loading:Boolean = false,
    val showData:Boolean = false,
    val exerciseInfoList:List<ExerciseInfoElem> = listOf(),
    val error:String? = null
)
