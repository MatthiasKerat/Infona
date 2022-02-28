package kerasinoapps.kapps.infona.auth_module.presentation.ui

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kerasinoapps.kapps.infona.R
import kerasinoapps.kapps.infona.auth_module.presentation.composables.AuthButton
import kerasinoapps.kapps.infona.auth_module.presentation.composables.TextEntryModul
import kerasinoapps.kapps.infona.auth_module.presentation.composables.TextTip
import kerasinoapps.kapps.infona.auth_module.presentation.state.AuthInputErrorType
import kerasinoapps.kapps.infona.auth_module.presentation.viewmodel.LoginViewModel
import kerasinoapps.kapps.infona.ui.theme.*

@ExperimentalComposeUiApi
@Composable
fun ResetPasswordDialog(
    onDissmiss:()->Unit,
    modifier: Modifier = Modifier,
    textValueCode:String,
    viewModel:LoginViewModel,
    textValueNewPassword:String,
    textValueNewPasswordRepeated:String,
    onTextValueCodeChanged:(String)->Unit,
    onTextValuePasswordChanged:(String)->Unit,
    onTextValuePasswordRepeatChanged:(String)->Unit,
    onSendCodeAgainClicked:()->Unit,
    onClick:()->Unit
) {
    Dialog(
        onDismissRequest = onDissmiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = modifier
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(25.dp, 20.dp, 25.dp, 20.dp)
            ) {
                val state = viewModel.resetPasswordState.value
                Text(
                    text = stringResource(id = R.string.reset_password_info),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = stringResource(id = R.string.send_code_again),
                    color = blau,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSendCodeAgainClicked()
                        }
                )
                TextEntryModul(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.request_code),
                    hint = stringResource(id = R.string.enter_reset_code),
                    imageVector = Icons.Default.Code,
                    value = textValueCode,
                    textColor = dunkelgrau,
                    cursorColor = blau,
                    textSize = 13,
                    textFieldSize = 14,
                    onValueChanged = onTextValueCodeChanged,
                    trailingIcon = null,
                    onTrailingIconClick = {}
                )

                TextEntryModul(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.password),
                    hint = stringResource(id = R.string.enter_password),
                    imageVector = Icons.Default.VpnKey,
                    value = textValueNewPassword,
                    textColor = dunkelgrau,
                    cursorColor = blau,
                    textSize = 13,
                    textFieldSize = 14,
                    onValueChanged = onTextValuePasswordChanged,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = null,
                    onTrailingIconClick = {}
                )
                TextEntryModul(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.repeat_password),
                    hint = stringResource(id = R.string.enter_repeated_password),
                    imageVector = Icons.Default.VpnKey,
                    value = textValueNewPasswordRepeated,
                    textColor = dunkelgrau,
                    cursorColor = blau,
                    textSize = 13,
                    textFieldSize = 14,
                    onValueChanged = onTextValuePasswordRepeatChanged,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = null,
                    onTrailingIconClick = {}
                )
                if(state.inputError){
                    when(state.inputErrorType){
                        AuthInputErrorType.EmptyField -> TextTip(stringResource(id = R.string.empty_fields))
                        AuthInputErrorType.PasswordToShort-> TextTip(stringResource(id = R.string.passwort_min_8))
                        AuthInputErrorType.PasswordsDoNotMatch -> TextTip(stringResource(id = R.string.passwords_do_not_match))
                        AuthInputErrorType.PasswordNoNumber -> TextTip(stringResource(id = R.string.password_at_least_one_number))
                        AuthInputErrorType.PasswordNoSpecialCharakter -> TextTip(stringResource(id = R.string.passwort_at_least_one_special_char))
                        AuthInputErrorType.EmailDoesntContainAtSymbol -> TextTip(stringResource(id = R.string.email_no_email))
                    }
                }else{
                    TextTip("")
                }
                if(state.loading){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        CircularProgressIndicator(
                            color = blau,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
                if(state.errorMessage != null){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            text = state.errorMessage,
                            color = rot,
                            fontSize = 13.sp
                        )
                    }
                }
                if(state.successful){
                    onDissmiss()
                    viewModel.initialResetPasswordState()
                    viewModel.toggleResetPasswordSnackbar()
                }
                AuthButton(
                    text = stringResource(id = R.string.reset_password),
                    backgroundColor = dunkelgrau,
                    contentColor = weiss,
                    fontSize = 17,
                    enabled = !state.inputError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        .height(45.dp)
                        .shadow(5.dp, RoundedCornerShape(25.dp)),
                    onButtonClick = onClick
                )
            }
        }
    }
}
