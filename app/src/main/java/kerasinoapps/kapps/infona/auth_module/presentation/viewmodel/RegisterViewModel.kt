package kerasinoapps.kapps.infona.auth_module.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.LoginRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.RegisterRequest
import kerasinoapps.kapps.infona.auth_module.domain.use_case.CheckRegisterInputUseCase
import kerasinoapps.kapps.infona.auth_module.domain.use_case.RegisterUseCase
import kerasinoapps.kapps.infona.auth_module.presentation.state.AuthInputErrorType
import kerasinoapps.kapps.infona.auth_module.presentation.state.AuthState
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.domain.use_case.UpdateUserUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val checkRegisterInputUseCase: CheckRegisterInputUseCase,
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel(){

    private val _registerState = mutableStateOf(AuthState())
    val registerState: State<AuthState> = _registerState

    private val _emailInput = mutableStateOf("")
    val emailInput:State<String> = _emailInput

    private val _passwordInput = mutableStateOf("")
    val passwordInput:State<String> = _passwordInput

    private val _passwordRepeatInput = mutableStateOf("")
    val passwordRepeatInput:State<String> = _passwordRepeatInput

    fun initRegisterState(){
        _registerState.value = AuthState(inputError = false, inputErrorType = AuthInputErrorType.EverythingFine)
    }

    fun onEmailChanged(newEmail:String){
        _emailInput.value = newEmail
        checkRegisterInput()
    }

    fun onPasswordChanged(newPasswort:String){
        _passwordInput.value = newPasswort
        checkRegisterInput()
    }

    fun onPasswordRepeatChanged(newPasswort: String){
        _passwordRepeatInput.value = newPasswort
        checkRegisterInput()
    }

    fun checkRegisterInput(){
        val result = checkRegisterInputUseCase(email = _emailInput.value, password = _passwordInput.value, passwordRepeat = _passwordRepeatInput.value)
        _registerState.value = _registerState.value.copy(inputError = (result != AuthInputErrorType.EverythingFine), inputErrorType = result)
    }

    fun register(){

        val user = User(email=_emailInput.value, isAppUser = true)
        registerUseCase(registerRequest = RegisterRequest(user,_passwordInput.value)).onEach { result ->

            when(result){
                is Resource.Success -> {
                    updateUserUseCase(user)
                    _registerState.value = _registerState.value.copy(loading = false, successful = true)
                }

                is Resource.Error -> {
                    _registerState.value = _registerState.value.copy(loading = false, errorMessage = result.message ?: "Unknown register error")
                }

                is Resource.Loading -> {
                    _registerState.value = _registerState.value.copy(loading = true, errorMessage = null)
                }
            }

        }.launchIn(viewModelScope)

    }

    private val _keyBoardTypePassword1 = mutableStateOf(KeyboardType.Ascii)
    val keyBoardTypePassword1:State<KeyboardType> = _keyBoardTypePassword1

    fun onToggleKeyBoardTypePassword1(){
        if(_keyBoardTypePassword1.value == KeyboardType.Ascii){
            _keyBoardTypePassword1.value = KeyboardType.Password
        }else{
            _keyBoardTypePassword1.value = KeyboardType.Ascii
        }

    }
    private val _keyBoardTypePassword2 = mutableStateOf(KeyboardType.Ascii)
    val keyBoardTypePassword2:State<KeyboardType> = _keyBoardTypePassword2

    fun onToggleKeyBoardTypePassword2(){
        if(_keyBoardTypePassword2.value == KeyboardType.Ascii){
            _keyBoardTypePassword2.value = KeyboardType.Password
        }else{
            _keyBoardTypePassword2.value = KeyboardType.Ascii
        }

    }

    private val _visualTransformation1 = mutableStateOf(true)
    val visualTransformation1:State<Boolean> = _visualTransformation1

    fun onToggleVisualTransformation1(){
        _visualTransformation1.value = !_visualTransformation1.value
    }
    private val _visualTransformation2 = mutableStateOf(true)
    val visualTransformation2:State<Boolean> = _visualTransformation2

    fun onToggleVisualTransformation2(){
        _visualTransformation2.value = !_visualTransformation2.value
    }

}