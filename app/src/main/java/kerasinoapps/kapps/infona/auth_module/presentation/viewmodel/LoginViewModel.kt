package kerasinoapps.kapps.infona.auth_module.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.LoginRequest
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.ResetPasswordRequest
import kerasinoapps.kapps.infona.auth_module.domain.use_case.*
import kerasinoapps.kapps.infona.auth_module.presentation.state.AuthInputErrorType
import kerasinoapps.kapps.infona.auth_module.presentation.state.AuthState
import kerasinoapps.kapps.infona.auth_module.presentation.state.ForgotPasswordState
import kerasinoapps.kapps.infona.common.Constants.REGISTER_DONE
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.data.local.preferences.UserPreferences
import kerasinoapps.kapps.infona.domain.use_case.UpdateUserUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val checkLoginInputUseCase: CheckLoginInputUseCase,
    private val checkUserEmailUseCase: CheckUserEmailUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val checkRegisterInputUseCase: CheckRegisterInputUseCase,
    private val userPreferences: UserPreferences,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel(){

    private val _loginState = mutableStateOf(AuthState())
    val loginState:State<AuthState> = _loginState

    private val _emailInput = mutableStateOf("")
    val emailInput:State<String> = _emailInput

    private val _passwordInput = mutableStateOf("")
    val passwordInput:State<String> = _passwordInput

    private val _stayLoggedIn = mutableStateOf(false)
    val stayLoggedIn:State<Boolean> = _stayLoggedIn

    private val _validatedEmail = mutableStateOf(false)
    val validatedEmail:State<Boolean> = _validatedEmail

    fun onEmailChanged(newEmail:String){
        _emailInput.value = newEmail
        checkLoginInput()
    }

    fun onPasswordChanged(newPasswort:String){
        _passwordInput.value = newPasswort
        checkLoginInput()
    }

    fun stayLoggedInToggle(){
        _stayLoggedIn.value = !_stayLoggedIn.value
    }

    fun initLoginState(){
        _loginState.value = AuthState(inputError = false, inputErrorType = AuthInputErrorType.EverythingFine)
    }

    fun checkLoginInput(){
        val result = checkLoginInputUseCase(email = _emailInput.value, password = _passwordInput.value)
        _loginState.value = _loginState.value.copy(inputError = (result != AuthInputErrorType.EverythingFine), inputErrorType = result)
    }

    fun login(){

        loginUseCase(loginRequest = LoginRequest(_emailInput.value,_passwordInput.value, _stayLoggedIn.value)).onEach { result ->

            when(result){
                is Resource.Success -> {
                    _validatedEmail.value = result.data!!.validated
                    val user = result.data!!.user.copy(isAppUser = true)
                    updateUserUseCase(user)
                    if(_stayLoggedIn.value){
                        userPreferences.saveRegisterStatus(REGISTER_DONE)
                    }
                    _loginState.value = _loginState.value.copy(loading = false, successful = true)
                }

                is Resource.Error -> {
                    _loginState.value = _loginState.value.copy(loading = false, errorMessage = result.message ?: "Unknown login error")
                }

                is Resource.Loading -> {
                    _loginState.value = _loginState.value.copy(loading = true, errorMessage = null)
                }
            }

        }.launchIn(viewModelScope)

    }

    /**
     * Forgot password Dialog
     */
    private val _forgotPasswordState = mutableStateOf(ForgotPasswordState())
    val forgotPasswordState:State<ForgotPasswordState> = _forgotPasswordState

    private val _showForgotPasswordDialog = mutableStateOf(false)
    val showForgotPasswordDialog:State<Boolean> = _showForgotPasswordDialog

    private val _forgotPasswordDialogTextInput = mutableStateOf("")
    val forgotPasswordDialogTextInput:State<String> = _forgotPasswordDialogTextInput

    fun onForgotPasswordDialogTextChange(newString:String){
        _forgotPasswordDialogTextInput.value = newString
    }

    fun toggleForgotPasswordDialogVisibility(){
        _showForgotPasswordDialog.value = !_showForgotPasswordDialog.value
    }

    fun initialForgetPasswordState(){
        _forgotPasswordState.value = ForgotPasswordState()
    }

    fun checkUserEmailExists(){
        checkUserEmailUseCase(_forgotPasswordDialogTextInput.value).onEach { result ->

            when(result){
                is Resource.Success ->{
                    if(result.data != null && result.data){
                        _forgotPasswordState.value = _forgotPasswordState.value.copy(true,false,false)
                    }else{
                        _forgotPasswordState.value = _forgotPasswordState.value.copy(true,true,false)
                    }
                }
                is Resource.Loading ->{
                    _forgotPasswordState.value = _forgotPasswordState.value.copy(false,false,true, null)
                }

                is Resource.Error ->{
                    _forgotPasswordState.value = _forgotPasswordState.value.copy(false,false,false, error = result.message ?: "An unknown error occured.")
                }
            }

        }.launchIn(viewModelScope)
    }


    /**
     * Reset password Dialog
     */

    private val _showResetPasswordDialog = mutableStateOf(false)
    val showResetPasswordDialog:State<Boolean> = _showResetPasswordDialog

    private val _showResetPasswordSnackbar = mutableStateOf(false)
    val showResetPasswordSnackbar:State<Boolean> = _showResetPasswordSnackbar

    private val _resetPasswordCode = mutableStateOf("")
    val resetPasswordCode:State<String> = _resetPasswordCode

    private val _resetPasswordPassword = mutableStateOf("")
    val resetPasswordPassword:State<String> = _resetPasswordPassword

    private val _resetPasswordPasswordRepeat = mutableStateOf("")
    val resetPasswordPasswordRepeat:State<String> = _resetPasswordPasswordRepeat

    private val _resetPasswordState = mutableStateOf(AuthState())
    val resetPasswordState:State<AuthState> = _resetPasswordState

    fun initialResetPasswordState(){
        _resetPasswordCode.value = ""
        _resetPasswordPassword.value = ""
        _resetPasswordPasswordRepeat.value = ""
        _resetPasswordState.value = AuthState()
    }

    fun onResetPasswordCodeChanged(newString:String){
        _resetPasswordCode.value = newString
        checkResetPasswordInput()
    }

    fun onResetPasswordPasswordChanged(newString:String){
        _resetPasswordPassword.value = newString
        checkResetPasswordInput()
    }

    fun onResetPasswordPasswordRepeatChanged(newString:String){
        _resetPasswordPasswordRepeat.value = newString
        checkResetPasswordInput()
    }

    fun toggleResetPasswordDialogVisibility(){
        _showResetPasswordDialog.value = !_showResetPasswordDialog.value
    }

    fun toggleResetPasswordSnackbar(){
        _showResetPasswordSnackbar.value = !_showResetPasswordSnackbar.value
    }

    fun resetPassword(){

        resetPasswordUseCase(ResetPasswordRequest(_forgotPasswordDialogTextInput.value,_resetPasswordCode.value,_resetPasswordPassword.value)).onEach { result->
            when(result){
                is Resource.Success ->{
                    if(result.data != null && result.data){
                        _resetPasswordState.value = _resetPasswordState.value.copy(successful = true,loading = false)
                    }else{
                        _resetPasswordState.value = _resetPasswordState.value.copy(false, errorMessage = result.message ?: "An unknown error occured", loading = false)
                    }
                }
                is Resource.Loading ->{
                    _resetPasswordState.value = _resetPasswordState.value.copy(successful = false, errorMessage = null, loading = true)
                }

                is Resource.Error ->{
                    _resetPasswordState.value = _resetPasswordState.value.copy(successful = false, errorMessage = result.message ?: "An unknown error occured", loading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun checkResetPasswordInput(){
        val result = checkRegisterInputUseCase(email = _resetPasswordCode.value, password = _resetPasswordPassword.value, passwordRepeat = _resetPasswordPasswordRepeat.value, considerEmail = false)
        _resetPasswordState.value = _resetPasswordState.value.copy(inputError = (result != AuthInputErrorType.EverythingFine), inputErrorType = result)
    }

    private val _keyBoardTypePassword = mutableStateOf(KeyboardType.Ascii)
    val keyBoardTypePassword:State<KeyboardType> = _keyBoardTypePassword

    fun onToggleKeyBoardTypePassword(){
        if(_keyBoardTypePassword.value == KeyboardType.Ascii){
            _keyBoardTypePassword.value = KeyboardType.Password
        }else{
            _keyBoardTypePassword.value = KeyboardType.Ascii
        }

    }

    private val _visualTransformation = mutableStateOf(true)
    val visualTransformation:State<Boolean> = _visualTransformation

    fun onToggleVisualTransformation(){
        _visualTransformation.value = !_visualTransformation.value
    }
}