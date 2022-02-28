package kerasinoapps.kapps.infona.auth_module.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import kerasinoapps.kapps.infona.R
import kerasinoapps.kapps.infona.auth_module.presentation.composables.AuthButton
import kerasinoapps.kapps.infona.auth_module.presentation.composables.CustomTextField
import kerasinoapps.kapps.infona.auth_module.presentation.viewmodel.LoginViewModel
import kerasinoapps.kapps.infona.ui.theme.*

@ExperimentalComposeUiApi
@Composable
fun ForgotPasswordDialog(
    onDissmiss:()->Unit,
    modifier: Modifier = Modifier,
    textValue:String,
    onTextValueChanged:(String)->Unit,
    viewModel:LoginViewModel,
    onClick:()->Unit
) {
    Dialog(
        onDismissRequest = onDissmiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){

        val state = viewModel.forgotPasswordState.value

        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = modifier
        ){

            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(25.dp,20.dp,25.dp,20.dp)
            ){
                CustomTextField(
                    value = textValue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, dunkelgrau, RoundedCornerShape(25.dp))
                        .height(50.dp)
                        .shadow(3.dp, RoundedCornerShape(25.dp))
                    ,
                    backgroundColor = weiss,
                    cursorColor = blau,
                    textColor = dunkelgrau,
                    icon = Icons.Default.Email,
                    placeholderText = stringResource(id = R.string.enter_email_adress),
                    textFieldSize = 13,
                    keyboardType = KeyboardType.Ascii,
                    visualTransformation = VisualTransformation.None,
                    onValueChanged = onTextValueChanged,
                    trailingIcon = null,
                    onTrailingIconClick = {}
                )
                Text(
                    text = stringResource(id = R.string.set_password_back_info),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
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
                if(state.userDoesNotExist){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            text = stringResource(id = R.string.no_user_exists),
                            color = rot,
                            fontSize = 13.sp
                        )
                    }
                }

                if(state.successful && !state.userDoesNotExist){
                    onDissmiss()
                    viewModel.toggleResetPasswordDialogVisibility()
                }

                if(state.error != null){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = state.error!!,
                            color = rot,
                            fontSize = 13.sp
                        )
                    }
                }

                AuthButton(
                    text = stringResource(id = R.string.request_code),
                    backgroundColor = dunkelgrau,
                    contentColor = weiss,
                    fontSize = 15,
                    onButtonClick = onClick,
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .height(45.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}
