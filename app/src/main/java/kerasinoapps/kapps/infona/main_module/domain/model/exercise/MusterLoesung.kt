package kerasinoapps.kapps.infona.main_module.domain.model.exercise

data class MusterLoesung(
    val loesungArray: List<String> = listOf(),
    val loesungMultipleChoice: List<Boolean> = listOf(),
    val loesungSingleChoice: Int = 0,
    val loesungString: String = "",
    val loesungsTyp: String
)