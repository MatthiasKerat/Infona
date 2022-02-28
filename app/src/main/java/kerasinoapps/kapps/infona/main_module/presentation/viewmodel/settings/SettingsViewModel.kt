package kerasinoapps.kapps.infona.main_module.presentation.viewmodel.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kerasinoapps.kapps.infona.common.Constants.REGISTER_UNDONE
import kerasinoapps.kapps.infona.data.local.preferences.UserPreferences
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.domain.use_case.GetApplicationUserUseCase
import kerasinoapps.kapps.infona.domain.use_case.UpdateUserUseCase
import kerasinoapps.kapps.infona.main_module.presentation.composables.settings.enums.DialogToShow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getApplicationUserUseCase: GetApplicationUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _user = mutableStateOf(User(email = ""))
    val user: State<User> = _user


    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
       getAppUser()
    }

    private fun getAppUser(){
        viewModelScope.launch {
            _user.value = getApplicationUserUseCase()
        }
    }

    fun onShowEmailAddressToggle(){
        val privacy = _user.value.privacy
        _user.value = _user.value.copy(privacy = listOf(!privacy.get(0),privacy.get(1),privacy.get(2)), isSynced = false)
        coroutineScope.launch {
            updateUserUseCase(_user.value)
        }
    }

    fun onShowAverageGradeToggle(){
        val privacy = _user.value.privacy
        _user.value = _user.value.copy(privacy = listOf(privacy.get(0),!privacy.get(1),privacy.get(2)), isSynced = false)
        coroutineScope.launch {
            updateUserUseCase(_user.value)
        }
    }

    fun onShowAgeToggle(){
        val privacy = _user.value.privacy
        _user.value = _user.value.copy(privacy = listOf(privacy.get(0),privacy.get(1),!privacy.get(2)), isSynced = false)
        coroutineScope.launch {
            updateUserUseCase(_user.value)
        }
    }


    /**
     * Dialogstuff
     */
    private val _showDialogError = mutableStateOf(true)
    val showDialogError:State<Boolean> = _showDialogError

    private val _showEditDialog = mutableStateOf(false)
    val showEditDialog:State<Boolean> = _showEditDialog

    private val _dialogToShow = mutableStateOf(DialogToShow.NAME)
    val dialogToShow:State<DialogToShow> = _dialogToShow

    private val _dialogNameInput = mutableStateOf("")
    val dialogNameInput:State<String> = _dialogNameInput

    private val _dialogGenderInput = mutableStateOf("")
    val dialogGenderInput:State<String> = _dialogGenderInput

    private val _dialogUniversityInput = mutableStateOf("")
    val dialogUniversityInput:State<String> = _dialogUniversityInput

    private val _dialogAverageGradeInput = mutableStateOf("")
    val dialogAverageGradeInput:State<String> = _dialogAverageGradeInput

    fun onNameInputChanged(newName: String){
        _dialogNameInput.value = newName
        setDialogError(newName.isBlank())
    }

    fun onGenderInputChanged(newGender: String){
        _dialogGenderInput.value = newGender
        if(newGender == "Female" || newGender == "Male"){
            setDialogError(false)
        }else{
            setDialogError(true)
        }
    }

    fun onUniversityInputChanged(newUniversity: String){
        _dialogUniversityInput.value = newUniversity
        setDialogError(newUniversity.isBlank())
    }

    fun onAverageGradeInputChanged(newAverageGrade: String){
        _dialogAverageGradeInput.value = newAverageGrade

        newAverageGrade.toFloatOrNull().let { grade ->
            if(grade==null || grade>6.0f){
                setDialogError(true)
            }else{
                setDialogError(false)
            }
        }
    }

    fun onNameChanged(){
        _user.value = _user.value.copy(name = _dialogNameInput.value)
        coroutineScope.launch {
            updateUserUseCase(_user.value)
        }
    }

    fun onGenderChanged(){
        _user.value = _user.value.copy(gender = _dialogGenderInput.value)
        coroutineScope.launch {
            updateUserUseCase(_user.value)
        }
    }

    fun onUniversityChanged(){
        _user.value = _user.value.copy(university = _dialogUniversityInput.value)
        coroutineScope.launch {
            updateUserUseCase(_user.value)
        }
    }

    fun onAverageGradeChanged(){
        _user.value = _user.value.copy(averageGrade = _dialogAverageGradeInput.value.toFloat())
        coroutineScope.launch {
            updateUserUseCase(_user.value)
        }
    }

    fun showEditDialog(dialogToShow: DialogToShow){
        _dialogToShow.value = dialogToShow
        _showEditDialog.value = true
    }

    fun dismissDialog(){
        _showEditDialog.value = false
        _showDialogError.value = true
        _dialogNameInput.value = ""
        _dialogAverageGradeInput.value = ""
        _dialogGenderInput.value = ""
        _dialogUniversityInput.value = ""
    }

    private fun setDialogError(boolean: Boolean){
        _showDialogError.value = boolean
    }


    fun onLogout(){
        userPreferences.saveRegisterStatus(REGISTER_UNDONE)
        _user.value = _user.value.copy(isAppUser = false)
        viewModelScope.launch {
            updateUserUseCase(_user.value)
        }
    }

}