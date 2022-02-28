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
import kerasinoapps.kapps.infona.auth_module.presentation.state.AuthInputErrorType
import kerasinoapps.kapps.infona.auth_module.presentation.viewmodel.RegisterViewModel
import kerasinoapps.kapps.infona.navigation.ScreenRoute
import kerasinoapps.kapps.infona.ui.theme.*

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val email = viewModel.emailInput.value
    val password = viewModel.passwordInput.value
    val passwordRepeat = viewModel.passwordRepeatInput.value
    val state = viewModel.registerState.value

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        ConstraintLayout {

            val (registerBox,signature,bottomText) = createRefs()

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
                    .padding(0.dp, 130.dp, 0.dp, 0.dp)
                    .constrainAs(registerBox) {
                        top.linkTo(parent.top)
                    },
                contentAlignment = Alignment.Center
            ){
                if(state.successful){
                    LaunchedEffect(key1 = true){
                        navController.navigate(ScreenRoute.ValidationScreen.route+"/${email}"){
                            popUpTo(ScreenRoute.RegisterScreen.route){inclusive = true}
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
                                    viewModel.initRegisterState()
                                },
                            text = stringResource(id = R.string.try_again),
                            color = blau,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                if(!state.loading && !state.successful && state.errorMessage == null){
                    RegisterContainer(
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
                        passwordRepeatValue = passwordRepeat,
                        inputErrorType = state.inputErrorType,
                        inputError = state.inputError,
                        onEmailChanged = {
                            viewModel.onEmailChanged(it)
                        },
                        onPasswordChanged = {
                            viewModel.onPasswordChanged(it)
                        },
                        onPasswordRepeatChanged = {
                            viewModel.onPasswordRepeatChanged(it)
                        },
                        onButtonClick = {
                            viewModel.register()
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
                        .height(230.dp)
                )
            }
            BottomText(
                text1 = stringResource(id = R.string.already_have_an_account),
                text2 = stringResource(id = R.string.login),
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
                navController.navigate(ScreenRoute.LoginScreen.route){
                    popUpTo(ScreenRoute.LoginScreen.route){inclusive = true}
                }
            }
        }
    }



}

@Composable
fun RegisterContainer(
    modifier: Modifier = Modifier,
    emailValue:String,
    passwordValue:String,
    passwordRepeatValue:String,
    inputErrorType:AuthInputErrorType,
    inputError:Boolean,
    onEmailChanged:(String)->Unit,
    onPasswordChanged:(String)->Unit,
    onPasswordRepeatChanged:(String)->Unit,
    onButtonClick:()->Unit,
    viewModel:RegisterViewModel
){
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
            onTrailingIconClick = {}
        )

        var visualTransformation1: VisualTransformation = VisualTransformation.None
        if(viewModel.visualTransformation1.value){
            visualTransformation1 = PasswordVisualTransformation()
        }
        var visualTransformation2: VisualTransformation = VisualTransformation.None
        if(viewModel.visualTransformation2.value){
            visualTransformation2 = PasswordVisualTransformation()
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
            keyboardType = viewModel.keyBoardTypePassword1.value,
            visualTransformation = visualTransformation1,
            trailingIcon = Icons.Default.RemoveRedEye,
            onTrailingIconClick = {
                viewModel.onToggleKeyBoardTypePassword1()
                viewModel.onToggleVisualTransformation1()
            }
        )
        TextEntryModul(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 0.dp),
            text = stringResource(id = R.string.repeat_password),
            hint = stringResource(id = R.string.enter_repeated_password),
            imageVector = Icons.Default.VpnKey,
            value = passwordRepeatValue,
            textColor = dunkelgrau,
            cursorColor = blau,
            textSize = 13,
            textFieldSize = 14,
            onValueChanged = onPasswordRepeatChanged,
            keyboardType = viewModel.keyBoardTypePassword2.value,
            visualTransformation = visualTransformation2,
            trailingIcon = Icons.Default.RemoveRedEye,
            onTrailingIconClick = {
                viewModel.onToggleKeyBoardTypePassword2()
                viewModel.onToggleVisualTransformation2()
            }
        )
        if(inputError){
            when(inputErrorType){
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
        AuthButton(
            text = stringResource(id = R.string.register),
            backgroundColor = blau,
            contentColor = weiss,
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