package kerasinoapps.kapps.infona.main_module.presentation.viewmodel.exercise

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.domain.model.user_info.BoughtTip
import kerasinoapps.kapps.infona.domain.use_case.GetApplicationUserUseCase
import kerasinoapps.kapps.infona.domain.use_case.UpdateUserUseCase
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Exercise
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Tipp
import kerasinoapps.kapps.infona.main_module.domain.use_case.CheckMultipleChoiceAnswerUseCase
import kerasinoapps.kapps.infona.main_module.domain.use_case.GetExercisesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getApplicationUserUseCase: GetApplicationUserUseCase,
    private val checkMultipleChoiceAnswerUseCase: CheckMultipleChoiceAnswerUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel(){

    private val _exercises = mutableStateOf<List<Exercise>>(listOf())
    val exercises: State<List<Exercise>> = _exercises

    private val _exerciseState = mutableStateOf(ExerciseState())
    val exerciseState: State<ExerciseState> = _exerciseState



    fun getExercises(topicId:String){

        getExercisesUseCase(topicId).onEach { result ->
            when(result){
                is Resource.Success ->{
                    //Aktualisierte Daten vom Server
                    _exercises.value = result.data!!
                    _exerciseState.value = _exerciseState.value.copy(showData = true, loading = false)
                    calculateExerciseToShow()
                }
                is Resource.Loading->{
                    if(result.data != null && result.data.isNotEmpty()){
                        //Daten von der lokalen Datenbank, die angezeigt werden können während vom Server noch geladen wird.
                        _exercises.value = result.data
                        _exerciseState.value = _exerciseState.value.copy(showData = true, loading = false)
                        calculateExerciseToShow()
                    }else{
                        _exerciseState.value = _exerciseState.value.copy(loading = true, showData = false, error = null)
                    }
                }
                is Resource.Error->{
                    _exerciseState.value = _exerciseState.value.copy(error = result.message ?: "An unknown error occured")
                }
            }
        }.launchIn(viewModelScope)
    }

    private val _showTipDialog = mutableStateOf(false)
    val showTipDialog:State<Boolean> = _showTipDialog

    fun onShowTipDialog(){
        _showTipDialog.value = true
    }
    fun onDismissTipDialog(){
        _showTipDialog.value = false
    }

    private val _showCheckSolutionDialog = mutableStateOf(false)
    val showCheckSolutionDialog:State<Boolean> = _showCheckSolutionDialog

    fun onShowCheckSolutionDialog(){
        _showCheckSolutionDialog.value = true
    }
    fun onDismissCheckSolutionDialog(){
        _showCheckSolutionDialog.value = false
    }



    /**
     * Current exercise
     */

    private val _exerciseToShow = mutableStateOf<Exercise?>(null)
    var exerciseToShow:State<Exercise?> = _exerciseToShow

    private val _multipleChoiceSelections = mutableStateOf<List<Boolean>>(listOf(false,false,false,false,false,false,false,false,false,false))
    val multipleChoiceSelections:State<List<Boolean>> = _multipleChoiceSelections

    private val _singleChoiceSelections = mutableStateOf<List<Boolean>>(listOf(false,false,false,false,false,false,false,false,false,false))
    val singleChoiceSelections:State<List<Boolean>> = _singleChoiceSelections

    private fun calculateExerciseToShow(){
        viewModelScope.launch {
            val user = getApplicationUserUseCase()
            for (exercise in _exercises.value){
                var found = false
                for(doneExercise in user.doneExercises){
                    if(exercise.id == doneExercise){
                        found = true
                    }
                }
                if(!found){
                    _exerciseToShow.value = exercise
                    for(tipp in _exerciseToShow.value!!.tipps){
                        for(boughtTipp in user.boughtTips){
                            if(tipp.id == boughtTipp.tipId){
                                tipp.alreadyBought = true
                            }
                        }
                    }
                    return@launch
                }
            }
            _exerciseToShow.value = _exercises.value[0]
            onFreezeScreen(true)
            onShowSolution()
        }

    }

    fun getCurrentExerciseNumber():Int{
        return _exercises.value.indexOf(_exerciseToShow.value)+1
    }

    fun changeMultipleChoiceSelection(index:Int, value:Boolean){
        val list = _multipleChoiceSelections.value.toMutableList()
        list[index] = value
        _multipleChoiceSelections.value = list
    }

    fun changeSingleChoiceSelection(index: Int, value: Boolean){
        val list = listOf(false,false,false,false,false,false,false,false,false,false).toMutableList()
        list[index] = value
        _singleChoiceSelections.value = list
    }

    private val _showSolutionCorrectSnackbar = mutableStateOf(false)
    val showSolutionCorrectSnackbar:State<Boolean> = _showSolutionCorrectSnackbar

    fun toggleShowSolutionCorrectSnackBar(){
        _showSolutionCorrectSnackbar.value = false
    }

    private val _showSolutionWrongSnackbar = mutableStateOf(false)
    val showSolutionWrongSnackbar:State<Boolean> = _showSolutionWrongSnackbar

    fun toggleShowSolutionWrongSnackBar(){
        _showSolutionWrongSnackbar.value = false
    }

    fun checkMultipleChoiceSolution(){
        if(_exerciseToShow.value != null){
            var index = 0
            for(bool in _exerciseToShow.value!!.musterLoesung.loesungMultipleChoice){
                if(bool != _multipleChoiceSelections.value[index]){
                    _showSolutionWrongSnackbar.value = true
                    _showCheckSolutionDialog.value = false
                    return
                }
                index++
            }
        }
        processCorrectAnswer()
        _showCheckSolutionDialog.value = false
    }

    fun checkSingleChoiceSolution(){
        if(_exerciseToShow.value != null){
            val index = _exerciseToShow.value!!.musterLoesung.loesungSingleChoice
            if(!_singleChoiceSelections.value[index]){
                _showSolutionWrongSnackbar.value = true
                _showCheckSolutionDialog.value = false
                return
            }
        }
        processCorrectAnswer()
        _showCheckSolutionDialog.value = false
    }

    fun checkSingleInputSolution(){
        if(_exerciseToShow.value != null){
            val value = _exerciseToShow.value!!.musterLoesung.loesungString
            if(_singleEntryInput.value != value){
                _showSolutionWrongSnackbar.value = true
                _showCheckSolutionDialog.value = false
                return
            }
        }
        processCorrectAnswer()
        _showCheckSolutionDialog.value = false
    }

    fun checkArrayInputSolution(){
        if(_exerciseToShow.value != null){
            var index = 0
            for(string in _exerciseToShow.value!!.musterLoesung.loesungArray){
                if(!_arrayEntryInput.value.contains(string)){
                    _showSolutionWrongSnackbar.value = true
                    _showCheckSolutionDialog.value = false

                    return
                }
                index++
            }
        }
        processCorrectAnswer()
        _showCheckSolutionDialog.value = false
    }

    fun processCorrectAnswer(){
        viewModelScope.launch {
            var user = getApplicationUserUseCase()
            val doneExercises = user.doneExercises.toMutableList()
            if(exerciseToShow.value!=null){
                doneExercises.add(exerciseToShow.value!!.id)
            }
            user = user.copy(doneExercises = doneExercises, coins = user.coins+_exerciseToShow.value!!.coinReward)
            updateUserUseCase(user)
        }
        _showSolutionCorrectSnackbar.value = true

    }

    private val _freezeScreen = mutableStateOf(false)
    val freezeScreen:State<Boolean> = _freezeScreen

    fun onFreezeScreen(bool:Boolean){
        _freezeScreen.value = bool
    }

    fun changeExerciseToShow(left:Boolean){

        val index = _exercises.value.indexOfFirst {
            it.id == _exerciseToShow.value?.id ?: 0
        }
        if(left){
            if(index>0){
                _exerciseToShow.value = _exercises.value[index-1]
                _singleChoiceSelections.value = listOf(false,false,false,false,false,false,false,false,false,false).toMutableList()
                _multipleChoiceSelections.value = listOf(false,false,false,false,false,false,false,false,false,false).toMutableList()
                _singleEntryInput.value = ""
            }
        }else{
            if(index < _exercises.value.size-1){
                _exerciseToShow.value = _exercises.value[index+1]
                _singleChoiceSelections.value = listOf(false,false,false,false,false,false,false,false,false,false).toMutableList()
                _multipleChoiceSelections.value = listOf(false,false,false,false,false,false,false,false,false,false).toMutableList()
                _singleEntryInput.value = ""
            }
        }
        viewModelScope.launch {
            val user = getApplicationUserUseCase()
            for (exercise in user.doneExercises){
                if(exercise == _exerciseToShow.value!!.id){
                    onFreezeScreen(true)
                    onShowSolution()
                    return@launch
                }
            }
            onFreezeScreen(false)
        }
    }

    fun onShowSolution(){
        if(_exerciseToShow.value == null){
            return
        }
        val exercise = _exerciseToShow.value!!
        when(exercise.exerciseInfo.exerciseTyp){
            "MultipleChoice"->{
                _multipleChoiceSelections.value = exercise.musterLoesung.loesungMultipleChoice
            }
            "SingleChoice"->{
                val list = _singleChoiceSelections.value.toMutableList()
                list[exercise.musterLoesung.loesungSingleChoice] = true
                _singleChoiceSelections.value = list
            }
            "SingleEntry"->{
                _singleEntryInput.value = exercise.musterLoesung.loesungString
            }
            "ArrayEntry"->{
                _arrayEntryInput.value = exercise.musterLoesung.loesungArray
            }
        }
    }

    /**
     * Single Entry
     */

    private val _singleEntryInput = mutableStateOf("")
    val singleEntryInput:State<String> = _singleEntryInput

    fun onSingleEntryInputChanged (newString:String){
        _singleEntryInput.value = newString
    }

    /**
     * Array Entry
     */

    private val _arrayEntryInput = mutableStateOf<List<String>>(listOf())
    val arrayEntryInput:State<List<String>> = _arrayEntryInput

    fun onArrayEntryInputChanged(index:Int, newString: String) {
        val list = _arrayEntryInput.value.toMutableList()
        if (list.size <= index) {
            list.add(index, newString)
        } else {
            list.set(index, newString)
        }

        _arrayEntryInput.value = list
    }

    /**
     * BUY TIPS
     */

    private val _revealedTipIds = mutableStateOf(listOf<String>())
    val revealedTipIds: State<List<String>> get() = _revealedTipIds

    fun onItemClickTipps(tippId: String) {

        if (_revealedTipIds.value.contains(tippId)) {
            _revealedTipIds.value = _revealedTipIds.value.toMutableList().also{ list ->
                list.remove(tippId)
            }
        }else{
            _revealedTipIds.value = _revealedTipIds.value.toMutableList().also { list ->
                list.add(tippId)
            }
        }
    }

    private val _showConfirmBuyTipDialog = mutableStateOf(false)
    val showConfirmBuyTipDialog:State<Boolean> = _showConfirmBuyTipDialog

    fun onShowConfirmBuyTipDialog(){
        _showConfirmBuyTipDialog.value = true
    }
    fun onDismissConfirmBuyTipDialog(){
        _showConfirmBuyTipDialog.value = false
    }

    private val _selectedTipToBuy = mutableStateOf<Tipp?>(null)
    val selectedTipToBuy:State<Tipp?> = _selectedTipToBuy

    fun onSelectedTipToBuyChange(tipp: Tipp){
        _selectedTipToBuy.value = tipp
    }

    private val _showGuthabenNotEnough = mutableStateOf<String?>(null)
    val showGuthabenNotEnought:State<String?> = _showGuthabenNotEnough
    //CHeck Guthaben für Tip kaufen, Lösung einreichen, TopicScreen Aufgabenanzahl, Profilbilder im Rankingscreen

    fun buyTip(tipp:Tipp){
        viewModelScope.launch {
            var user = getApplicationUserUseCase()
            if(user.coins<tipp.coinCost){
                _showGuthabenNotEnough.value = "Guthaben (${user.coins} IC$) reicht nicht aus, um den Tipp zu kaufen."
                return@launch
            }
            val boughtTipList = user.boughtTips.toMutableList()
            boughtTipList.add(BoughtTip(exerciseToShow.value!!.id,tipp.id))
            user = user.copy(boughtTips = boughtTipList, coins = user.coins-tipp.coinCost)
            updateUserUseCase(user)
            val tipps = _exerciseToShow.value!!.tipps.toMutableList()
            tipps.first {
                it.id == tipp.id
            }.apply {
                alreadyBought = true
            }
            _exerciseToShow.value = _exerciseToShow.value!!.copy(tipps = tipps)
            _showTipDialog.value=false
            _showTipDialog.value=true
        }
    }

    fun toggleShowGuthabenNotEnoughSnackbar(){
        _showGuthabenNotEnough.value = null
    }
}