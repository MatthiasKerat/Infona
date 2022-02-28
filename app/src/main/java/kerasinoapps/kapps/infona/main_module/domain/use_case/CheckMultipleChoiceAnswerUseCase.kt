package kerasinoapps.kapps.infona.main_module.domain.use_case

import kerasinoapps.kapps.infona.main_module.domain.model.exercise.MusterLoesung
import javax.inject.Inject

class CheckMultipleChoiceAnswerUseCase @Inject constructor(

) {

    operator fun invoke(musterLoesung: MusterLoesung, selectionArray:List<Boolean>):Boolean{
        if(musterLoesung.loesungsTyp != "MultipleChoice"){
            return false
        }
        var i = 0
        for(loesung in musterLoesung.loesungMultipleChoice){
            if(loesung != selectionArray[i]){
                return false
            }
            i++
        }
        return true
    }

}