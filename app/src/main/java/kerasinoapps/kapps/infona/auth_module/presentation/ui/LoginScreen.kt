package kerasinoapps.kapps.infona.auth_module.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kerasinoapps.kapps.infona.R
import kerasinoapps.kapps.infona.auth_module.presentation.composables.*
import kerasinoapps.kapps.infona.auth_module.presentation.viewmodel.LoginViewModel
import kerasinoapps.kapps.infona.navigation.ScreenRoute
import kerasinoapps.kapps.infona.ui.theme.*

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel:LoginViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val email = viewModel.emailInput.value
    val password = viewModel.passwordInput.value
    val stayLoggedIn = viewModel.stayLoggedIn.value
    val state = viewModel.loginState.value

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        ConstraintLayout {

            val (loginBox,signature,bottomText) = createRefs()

            HeaderBackground(
                topColor = dunkelgrau,
                bottomColor = hellblau,
                modifier = Modifier
                    .fillMaxSize()
            )
            Header(
                text = stringResource(id = R.string.app_name),
                fontSize = 23,
                textColor = weiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 35.dp, 0.dp, 0.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 140.dp, 0.dp, 0.dp)
                    .constrainAs(loginBox) {
                        top.linkTo(parent.top)
                    },
                contentAlignment = Alignment.Center
            ){
                if(state.successful && viewModel.validatedEmail.value){
                    LaunchedEffect(key1 = true){
                        navController.navigate(ScreenRoute.MainMenuScreen.route){
                            popUpTo(ScreenRoute.LoginScreen.route){inclusive = true}
                        }
                    }
                }else if(state.successful && !viewModel.validatedEmail.value){

                    LaunchedEffect(key1 = true){
                        navController.navigate(ScreenRoute.ValidationScreen.route+"/${email}"){
                            popUpTo(ScreenRoute.LoginScreen.route){inclusive = true}
                        }
                    }

                }
                if(state.loading){
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = blau
                    )
                }
                if(state.errorMessage != null){
                    Column(
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            text = state.errorMessage,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clickable {
                                    viewModel.initLoginState()
                                },
                            text = stringResource(id = R.string.try_again),
                            color = blau,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                if(!state.loading && !state.successful && state.errorMessage == null){
                    LoginContainer(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .shadow(5.dp, RoundedCornerShape(15.dp))
                            .background(
                                background_hellgrau,
                                RoundedCornerShape(15.dp)
                            )
                            .padding(0.dp, 15.dp, 0.dp, 15.dp),
                        emailValue = email,
                        passwordValue = password,
                        stayLoggedInSelected = stayLoggedIn,
                        buttonEnabled = !viewModel.loginState.value.inputError,
                        onForgotPasswordClicked = {
                            viewModel.toggleForgotPasswordDialogVisibility()
                        },
                        onEmailChanged = {
                            viewModel.onEmailChanged(it)
                        },
                        onPasswordChanged = {
                            viewModel.onPasswordChanged(it)
                        },
                        onButtonClick = {
                            viewModel.login()
                        },
                        onStayLoggedInToggle = {
                            viewModel.stayLoggedInToggle()
                        },
                        viewModel = viewModel
                    )
                }
            }


            Signature(
                text1 = stringResource(id = R.string.created),
                text2 = stringResource(id = R.string.by),
                text3 = stringResource(id = R.string.matthias_kerat),
                primaryColor = hellgrau,
                secondaryColor = hellblau,
                primaryFontSize = 13,
                secondaryFontSize = 10,
                modifier = Modifier
                    .padding(bottom = 100.dp)
                    .constrainAs(signature) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
                BubbleAnimation(
                    bubbleColor1 = blau,
                    bubbleColor2 = hellblau,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }
            BottomText(
                text1 = stringResource(id = R.string.no_account_yet),
                text2 = stringResource(id = R.string.register),
                fontSize = 13,
                colorText1 = dunkelgrau,
                colorText2 = blau,
                modifier= Modifier
                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    .fillMaxWidth()
                    .constrainAs(bottomText) {
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                navController.navigate(ScreenRoute.RegisterScreen.route){
                    popUpTo(ScreenRoute.RegisterScreen.route){inclusive = true}
                }
            }
        }
        if(viewModel.showForgotPasswordDialog.value){
            ForgotPasswordDialog(
                onDissmiss = {
                    viewModel.toggleForgotPasswordDialogVisibility()
                    viewModel.initialForgetPasswordState()
                },
                textValue = viewModel.forgotPasswordDialogTextInput.value,
                onTextValueChanged = {
                    viewModel.onForgotPasswordDialogTextChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth(0.95f),
                viewModel = viewModel,
                onClick = {
                    viewModel.checkUserEmailExists()
                }
            )
        }
        if(viewModel.showResetPasswordDialog.value){
            ResetPasswordDialog(
                onDissmiss = {
                    viewModel.toggleResetPasswordDialogVisibility()
                    viewModel.initialResetPasswordState()
                },
                modifier = Modifier
                    .fillMaxWidth(0.95f),
                textValueCode = viewModel.resetPasswordCode.value,
                textValueNewPassword = viewModel.resetPasswordPassword.value,
                textValueNewPasswordRepeated = viewModel.resetPasswordPasswordRepeat.value,
                viewModel = viewModel,
                onTextValueCodeChanged = {
                    viewModel.onResetPasswordCodeChanged(it)
                },
                onTextValuePasswordChanged = {
                    viewModel.onResetPasswordPasswordChanged(it)
                },
                onTextValuePasswordRepeatChanged = {
                    viewModel.onResetPasswordPasswordRepeatChanged(it)
                },
                onSendCodeAgainClicked = {
                    //Serveranfrage um den Code erneut zu senden
                },
                onClick = {
                    viewModel.resetPassword()
                })
        }

        val resetPasswordSnackBarMessage = stringResource(id = R.string.successfully_reset_password)
        val closeString = stringResource(id = R.string.close)
        LaunchedEffect(viewModel.showResetPasswordSnackbar.value){
            if(viewModel.showResetPasswordSnackbar.value){
                scaffoldState.snackbarHostState.showSnackbar(
                    message = resetPasswordSnackBarMessage,
                    actionLabel = closeString,
                    duration = SnackbarDuration.Short
                )
                viewModel.toggleResetPasswordSnackbar()
            }
        }
    }


}

@Composable
fun LoginContainer(
    modifier: Modifier = Modifier,
    emailValue:String,
    passwordValue:String,
    stayLoggedInSelected:Boolean,
    buttonEnabled:Boolean,
    onEmailChanged:(String)->Unit,
    onForgotPasswordClicked: ()->Unit,
    onPasswordChanged:(String)->Unit,
    onButtonClick:()->Unit,
    onStayLoggedInToggle:()->Unit,
    viewModel: LoginViewModel
) {
    
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        TextEntryModul(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 0.dp),
            text = stringResource(id = R.string.email_address),
            hint = stringResource(id = R.string.enter_email_adress),
            imageVector = Icons.Default.Email,
            value = emailValue,
            textColor = dunkelgrau,
            cursorColor = blau,
            textSize = 13,
            textFieldSize = 14,
            onValueChanged = onEmailChanged,
            trailingIcon = null,
            onTrailingIconClick = {

            }
        )
        var visualTransformation:VisualTransformation = VisualTransformation.None
        if(viewModel.visualTransformation.value){
            visualTransformation = PasswordVisualTransformation()
        }
        TextEntryModul(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 0.dp),
            text = stringResource(id = R.string.password),
            hint = stringResource(id = R.string.enter_password),
            imageVector = Icons.Default.VpnKey,
            value = passwordValue,
            textColor = dunkelgrau,
            cursorColor = blau,
            textSize = 13,
            textFieldSize = 14,
            onValueChanged = onPasswordChanged,
            keyboardType = viewModel.keyBoardTypePassword.value,
            visualTransformation = visualTransformation,
            trailingIcon = Icons.Default.RemoveRedEye,
            onTrailingIconClick = {
                viewModel.onToggleKeyBoardTypePassword()
                viewModel.onToggleVisualTransformation()
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            RadioButtonWithText(
                modifier = Modifier
                    .padding(10.dp,0.dp,0.dp,0.dp),
                text = stringResource(id = R.string.stay_logged_in),
                buttonColor = blau,
                selected = stayLoggedInSelected,
                fontSize = 12
            ) {
                onStayLoggedInToggle()
            }
            Text(
                modifier = Modifier
                    .padding(end = 15.dp)
                    .clickable {
                        onForgotPasswordClicked()
                    },
                text = stringResource(id = R.string.forgot_password),
                textAlign = TextAlign.Center,
                fontSize = 10.sp
            )
        }
        AuthButton(
            text = stringResource(id = R.string.login),
            backgroundColor = blau,
            contentColor = weiss,
            enabled = buttonEnabled,
            fontSize = 17,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp, 10.dp, 0.dp)
                .height(45.dp)
                .shadow(5.dp, RoundedCornerShape(25.dp)),
            onButtonClick = onButtonClick
        )
    }
    
}