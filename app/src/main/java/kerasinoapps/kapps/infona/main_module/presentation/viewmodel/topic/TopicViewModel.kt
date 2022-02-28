package kerasinoapps.kapps.infona.main_module.presentation.viewmodel.topic

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.domain.use_case.GetApplicationUserUseCase
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kerasinoapps.kapps.infona.main_module.domain.model.Topic
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Exercise
import kerasinoapps.kapps.infona.main_module.domain.use_case.GetAllExercisesUseCase
import kerasinoapps.kapps.infona.main_module.domain.use_case.GetExercisesUseCase
import kerasinoapps.kapps.infona.main_module.domain.use_case.GetTopicsUseCase
import kerasinoapps.kapps.infona.main_module.presentation.viewmodel.profile.ExerciseInfoElem
import kerasinoapps.kapps.infona.main_module.presentation.viewmodel.profile.ProfileState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val getTopicsUseCase: GetTopicsUseCase,
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val getApplicationUserUseCase: GetApplicationUserUseCase
): ViewModel() {

    private val _topics = mutableStateOf<List<Topic>>(listOf())
    val topics: State<List<Topic>> = _topics

    private val _topicState = mutableStateOf(TopicState())
    val topicState: State<TopicState> = _topicState

    /*fun getTopics(subjectId:String){

        getTopicsUseCase(subjectId).onEach { result ->
            when(result){
                is Resource.Success ->{
                    //Aktualisierte Daten vom Server
                    _topics.value = result.data!!

                }
                is Resource.Loading->{
                    if(result.data != null && result.data.isNotEmpty()){
                        //Daten von der lokalen Datenbank, die angezeigt werden können während vom Server noch geladen wird.
                        _topics.value = result.data
                        _topicState.value = _topicState.value.copy(showData = true, loading = false)
                    }else{
                        _topicState.value = _topicState.value.copy(loading = true, showData = false, error = null)
                    }
                }
                is Resource.Error->{
                    _topicState.value = _topicState.value.copy(error = result.message ?: "An unknown error occured")
                }
            }
        }.launchIn(viewModelScope)
    }*/

    private var exercises = listOf<Exercise>()

    fun getValues(subjectId: String){
        var alreadyCalculated = false
        viewModelScope.launch {
            combine(getAllExercisesUseCase(), getTopicsUseCase(subjectId)){ result1, result2->
                when(result1){
                    is Resource.Success ->{
                        exercises = result1.data!!
                    }
                }
                when(result2){
                    is Resource.Success ->{
                        _topics.value = result2.data!!
                    }
                    is Resource.Loading ->{
                        if(result2.data != null && result2.data.isNotEmpty()){
                            //Daten von der lokalen Datenbank, die angezeigt werden können während vom Server noch geladen wird.
                            _topics.value = result2.data
                            calculateExerciseInfo()
                            _topicState.value = _topicState.value.copy(showData = true, loading = false)
                        }else{
                            _topicState.value = _topicState.value.copy(loading = true, showData = false, error = null)
                        }
                    }
                    is Resource.Error ->{
                        _topicState.value = _topicState.value.copy(error = result2.message ?: "An unknown error occured")
                    }
                }
            }.collect {

                if(exercises.isNotEmpty()&&_topics.value.isNotEmpty()){
                    if (!alreadyCalculated){
                        calculateExerciseInfo()
                        alreadyCalculated = true
                    }
                }
            }
        }
    }

    private suspend fun calculateExerciseInfo(){
        var list = listOf<ExerciseInfoElem>()
        val user = getApplicationUserUseCase()
        for(topic in _topics.value){
            var counter = 0
            var doneCounter = 0
            val exerciseInfoElem = ExerciseInfoElem(topic.name,0,0)
            for(exercise in exercises){
                if(exercise.belongsToTopic == topic.id){
                    counter++
                    if(user.doneExercises.contains(exercise.id)){
                        doneCounter++
                    }
                }
            }
            exerciseInfoElem.exerciseCount = counter
            exerciseInfoElem.doneExercises = doneCounter
            list = list + exerciseInfoElem
        }
        _topicState.value = _topicState.value.copy(showData = true, loading = false, exerciseInfoList = list)
    }

}