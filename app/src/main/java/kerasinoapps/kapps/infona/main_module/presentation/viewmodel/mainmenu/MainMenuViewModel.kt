package kerasinoapps.kapps.infona.main_module.presentation.viewmodel.mainmenu

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.domain.use_case.GetApplicationUserUseCase
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kerasinoapps.kapps.infona.main_module.domain.use_case.GetSubjectsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val getApplicationUserUseCase: GetApplicationUserUseCase,
    private val getSubjectsUseCase: GetSubjectsUseCase
) :ViewModel(){


    private val _initalizationLetter = mutableStateOf("")
    val initalizationLetter: State<String> = _initalizationLetter

    private val _subjects = mutableStateOf<List<Subject>>(listOf())
    val subjects:State<List<Subject>> = _subjects

    private val _mainMenuState = mutableStateOf(MainMenuState())
    val mainMenuState:State<MainMenuState> = _mainMenuState

    var appUser: User? = null

    init {
        getAppUser()
        getSubjects()
    }

    private fun getAppUser(){
        viewModelScope.launch {
            val user = getApplicationUserUseCase()
            _initalizationLetter.value = user.name.substring(0,1)
            appUser = user
        }
    }

    fun getSubjects(){

        getSubjectsUseCase().onEach { result ->
            when(result){
                is Resource.Success ->{
                    //Aktualisierte Daten vom Server
                    _subjects.value = result.data!!
                    _mainMenuState.value = _mainMenuState.value.copy(showData = true, loading = false)
                }
                is Resource.Loading->{
                    if(result.data != null && result.data.isNotEmpty()){
                        //Daten von der lokalen Datenbank, die angezeigt werden können während vom Server noch geladen wird.
                        _subjects.value = result.data
                        _mainMenuState.value = _mainMenuState.value.copy(showData = true, loading = false)
                    }else{
                        _mainMenuState.value = _mainMenuState.value.copy(loading = true, showData = false, error = null)
                    }
                }
                is Resource.Error->{
                    _mainMenuState.value = _mainMenuState.value.copy(error = result.message ?: "An unknown error occured")
                }
            }
        }.launchIn(viewModelScope)
    }

}