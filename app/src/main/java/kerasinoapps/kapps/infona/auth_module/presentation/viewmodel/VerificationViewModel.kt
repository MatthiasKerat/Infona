package kerasinoapps.kapps.infona.auth_module.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.request.ValidateEmailRequest
import kerasinoapps.kapps.infona.auth_module.domain.use_case.RequestVerificationCodeUseCase
import kerasinoapps.kapps.infona.auth_module.domain.use_case.ValidateEmailUseCase
import kerasinoapps.kapps.infona.auth_module.presentation.state.SendVerificationCodeState
import kerasinoapps.kapps.infona.auth_module.presentation.state.ValidateEmailState
import kerasinoapps.kapps.infona.common.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val sendVerificationCodeUseCase: RequestVerificationCodeUseCase
):ViewModel(){

    private val _inputField1 = mutableStateOf("")
    val inputField1: State<String> = _inputField1

    private val _inputField2 = mutableStateOf("")
    val inputField2: State<String> = _inputField2

    private val _inputField3 = mutableStateOf("")
    val inputField3: State<String> = _inputField3

    private val _inputField4 = mutableStateOf("")
    val inputField4: State<String> = _inputField4

    private val _inputField5 = mutableStateOf("")
    val inputField5: State<String> = _inputField5

    private val _inputField6 = mutableStateOf("")
    val inputField6: State<String> = _inputField6

    private val _emailAddress = mutableStateOf("")
    val emailAddress:State<String> = _emailAddress

    private val _validateEmailState = mutableStateOf(ValidateEmailState())
    val validateEmailState:State<ValidateEmailState> = _validateEmailState

    private val _sendVerificationCodeState = mutableStateOf(SendVerificationCodeState())
    val sendVerificationCodeState:State<SendVerificationCodeState> = _sendVerificationCodeState

    fun onTextField1Changed(newString:String){
        _inputField1.value = newString
        checkButtonEnabled()
    }

    fun onTextField2Changed(newString:String){
        _inputField2.value = newString
        checkButtonEnabled()
    }

    fun onTextField3Changed(newString:String){
        _inputField3.value = newString
        checkButtonEnabled()
    }

    fun onTextField4Changed(newString:String){
        _inputField4.value = newString
        checkButtonEnabled()
    }

    fun onTextField5Changed(newString:String){
        _inputField5.value = newString
        checkButtonEnabled()
    }

    fun onTextField6Changed(newString:String){
        _inputField6.value = newString
        checkButtonEnabled()
    }

    fun onEmailAddressChanged(newString: String){
        _emailAddress.value = newString
    }

    fun checkButtonEnabled(){
        if(_inputField1.value.isNotBlank()&&_inputField2.value.isNotBlank()
            &&_inputField3.value.isNotBlank()&&_inputField4.value.isNotBlank()&&
            _inputField5.value.isNotBlank()&&_inputField6.value.isNotBlank()){
            _validateEmailState.value = _validateEmailState.value.copy(buttonEnabled = true)
        }else{
            _validateEmailState.value = _validateEmailState.value.copy(buttonEnabled = false)
        }
    }

    fun validateEmail(){
        validateEmailUseCase(ValidateEmailRequest(_emailAddress.value,_inputField1.value+_inputField2.value+_inputField3.value+_inputField4.value+_inputField5.value+_inputField6.value)).onEach { result ->
            when(result){
                is Resource.Success ->{
                    if(result.data != null && result.data){
                        _validateEmailState.value = _validateEmailState.value.copy(successful = true,loading = false)
                    }else{
                        _validateEmailState.value = _validateEmailState.value.copy(false, error = result.message ?: "An unknown error occured", loading = false)
                    }
                }
                is Resource.Loading ->{
                    _validateEmailState.value = _validateEmailState.value.copy(successful = false, error = null, loading = true)
                }

                is Resource.Error ->{
                    _validateEmailState.value = _validateEmailState.value.copy(successful = false, error = result.message ?: "An unknown error occured", loading = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sendVerificationCode(){
        sendVerificationCodeUseCase(_emailAddress.value).onEach { result ->

            when(result){
                is Resource.Success ->{
                    if(result.data != null && result.data){
                        _sendVerificationCodeState.value = _sendVerificationCodeState.value.copy(successful = true,loading = false)
                    }else{
                        _sendVerificationCodeState.value = _sendVerificationCodeState.value.copy(false, error = result.message ?: "An unknown error occured", loading = false)
                    }
                }
                is Resource.Loading ->{
                    _sendVerificationCodeState.value = _sendVerificationCodeState.value.copy(successful = false, error = null, loading = true)
                }

                is Resource.Error ->{
                    _sendVerificationCodeState.value = _sendVerificationCodeState.value.copy(successful = false, error = result.message ?: "An unknown error occured", loading = false)
                }
            }

        }.launchIn(viewModelScope)
    }
}