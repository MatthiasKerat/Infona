package kerasinoapps.kapps.infona.auth_module.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kerasinoapps.kapps.infona.common.Constants.REGISTER_DONE
import kerasinoapps.kapps.infona.common.convertMonthToInt
import kerasinoapps.kapps.infona.data.local.preferences.UserPreferences
import kerasinoapps.kapps.infona.domain.model.user_info.Birthday
import kerasinoapps.kapps.infona.domain.use_case.GetApplicationUserUseCase
import kerasinoapps.kapps.infona.domain.use_case.UpdateUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasicDataViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val getApplicationUserUseCase: GetApplicationUserUseCase,
    private val userPreferences: UserPreferences,
) : ViewModel(){

    private val _nameInput = mutableStateOf("")
    val nameInput: State<String> = _nameInput

    private val _selectedGender = mutableStateOf<String>("Female")
    val selectedGender:State<String> = _selectedGender

    private val _selectedDay = mutableStateOf("15")
    val selectedDay:State<String> = _selectedDay

    private val _selectedMonth =  mutableStateOf("Jun")
    val selectedMonth:State<String> = _selectedMonth

    private val _selectedYear =  mutableStateOf("1992")
    val selectedYear:State<String> = _selectedYear

    private val _selectedGrade =  mutableStateOf("3.0")
    val selectedGrade:State<String> = _selectedGrade

    private val _loading = mutableStateOf(false)
    val loading:State<Boolean> = _loading

    private val _continueToMainMenu = mutableStateOf(false)
    val continueToMainMenu:State<Boolean> = _continueToMainMenu

    private val _universityInput = mutableStateOf("")
    val universityInput:State<String> = _universityInput

    fun onNameChanged(newString:String){
        _nameInput.value = newString
    }

    fun onUniversityChanged(newString:String){
        _universityInput.value = newString
    }

    fun onGenderChanged(newGender:String){
        _selectedGender.value = newGender
    }

    fun onDayChanged(newDay: String){
        _selectedDay.value = newDay
    }
    fun onMonthChanged(newMonth: String){
        _selectedMonth.value = newMonth
    }
    fun onYearChanged(newYear: String){
        _selectedYear.value = newYear
    }
    fun onGradeChanged(newGrade:String){
        _selectedGrade.value = newGrade
    }

    fun saveUserData(){
        viewModelScope.launch {
            _loading.value = true
            val appUser = getApplicationUserUseCase()
            val userToSave = appUser.copy(
                name = _nameInput.value,
                gender = _selectedGender.value,
                birthday = Birthday(day = _selectedDay.value.toInt(),month = convertMonthToInt(_selectedMonth.value),year = _selectedYear.value.toInt()),
                university = _universityInput.value,
                averageGrade = _selectedGrade.value.toFloat(),
                isSynced = false
            )
            updateUserUseCase(userToSave)
            userPreferences.saveRegisterStatus(REGISTER_DONE)
            _loading.value = false
            _continueToMainMenu.value = true

        }
    }
}