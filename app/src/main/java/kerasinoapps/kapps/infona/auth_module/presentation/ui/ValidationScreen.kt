package kerasinoapps.kapps.infona.auth_module.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kerasinoapps.kapps.infona.R
import kerasinoapps.kapps.infona.auth_module.presentation.composables.AuthButton
import kerasinoapps.kapps.infona.auth_module.presentation.composables.InitializationHeader
import kerasinoapps.kapps.infona.auth_module.presentation.composables.VerificationInputField
import kerasinoapps.kapps.infona.auth_module.presentation.viewmodel.VerificationViewModel
import kerasinoapps.kapps.infona.navigation.ScreenRoute
import kerasinoapps.kapps.infona.ui.theme.*

@Composable
fun ValidationScreen(
    navController: NavController,
    viewModel:VerificationViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    userEmail:String?
) {

    LaunchedEffect(key1 = true){
        viewModel.onEmailAddressChanged(userEmail ?: "")
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        BoxWithConstraints {
            val width = this.maxWidth.value
            val height = this.maxHeight.value
            val text1 = viewModel.inputField1.value
            val text2 = viewModel.inputField2.value
            val text3 = viewModel.inputField3.value
            val text4 = viewModel.inputField4.value
            val text5 = viewModel.inputField5.value
            val text6 = viewModel.inputField6.value
            val state = viewModel.validateEmailState.value
            val sendVerificationCodeState = viewModel.sendVerificationCodeState.value
            val sendVerificationCodeMessage = stringResource(id = R.string.send_verification_code_msg)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = weiss),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                InitializationHeader(
                    text = stringResource(id = R.string.email_verification),
                    modifier = Modifier
                        .padding(top = Dp(height*0.1f)),
                    fontSize = 30
                )
                Row(
                    modifier = Modifier
                        .padding(end = 10.dp, start = 10.dp, top = Dp(height * 0.1f))
                        .fillMaxWidth()
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    VerificationInputField(
                        value = text1,
                        textColor = dunkelgrau,
                        textFieldSize = 40,
                        onTextChanged = {
                            if(it.length<=1){
                                viewModel.onTextField1Changed(it)
                            }
                        },
                        modifier = Modifier
                            .border(1.dp, color = dunkelgrau, RoundedCornerShape(20.dp))
                            .shadow(3.dp, RoundedCornerShape(20.dp))
                            .width(Dp(width * 0.13f))
                            .aspectRatio(0.9f)
                            .background(color = weiss)
                    )
                    VerificationInputField(
                        value = text2,
                        textColor = dunkelgrau,
                        textFieldSize = 40,
                        onTextChanged = {
                            if(it.length<=1){
                                viewModel.onTextField2Changed(it)
                            }
                        },
                        modifier = Modifier
                            .border(1.dp, color = dunkelgrau, RoundedCornerShape(20.dp))
                            .shadow(3.dp, RoundedCornerShape(20.dp))
                            .width(Dp(width * 0.13f))
                            .aspectRatio(0.9f)
                            .background(color = weiss)
                    )
                    VerificationInputField(
                        value = text3,
                        textColor = dunkelgrau,
                        textFieldSize = 40,
                        onTextChanged = {
                            if(it.length<=1){
                                viewModel.onTextField3Changed(it)
                            }
                        },
                        modifier = Modifier
                            .border(1.dp, color = dunkelgrau, RoundedCornerShape(20.dp))
                            .shadow(3.dp, RoundedCornerShape(20.dp))
                            .width(Dp(width * 0.13f))
                            .aspectRatio(0.9f)
                            .background(color = weiss)
                    )
                    VerificationInputField(
                        value = text4,
                        textColor = dunkelgrau,
                        textFieldSize = 40,
                        onTextChanged = {
                            if(it.length<=1){
                                viewModel.onTextField4Changed(it)
                            }
                        },
                        modifier = Modifier
                            .border(1.dp, color = dunkelgrau, RoundedCornerShape(20.dp))
                            .shadow(3.dp, RoundedCornerShape(20.dp))
                            .width(Dp(width * 0.13f))
                            .aspectRatio(0.9f)
                            .background(color = weiss)
                    )
                    VerificationInputField(
                        value = text5,
                        textColor = dunkelgrau,
                        textFieldSize = 40,
                        onTextChanged = {
                            if(it.length<=1){
                                viewModel.onTextField5Changed(it)
                            }
                        },
                        modifier = Modifier
                            .border(1.dp, color = dunkelgrau, RoundedCornerShape(20.dp))
                            .shadow(3.dp, RoundedCornerShape(20.dp))
                            .width(Dp(50f))
                            .aspectRatio(0.9f)
                            .background(color = weiss)
                    )
                    VerificationInputField(
                        value = text6,
                        textColor = dunkelgrau,
                        textFieldSize = 40,
                        onTextChanged = {
                            if(it.length<=1){
                                viewModel.onTextField6Changed(it)
                            }
                        },
                        modifier = Modifier
                            .border(1.dp, color = dunkelgrau, RoundedCornerShape(20.dp))
                            .shadow(3.dp, RoundedCornerShape(20.dp))
                            .width(Dp(width * 0.13f))
                            .aspectRatio(0.9f)
                            .background(color = weiss)
                    )
                }
                Text(
                    text = stringResource(id = R.string.verification_info),
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp,top = 25.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp
                )

                Text(
                    text = stringResource(id = R.string.send_code_again),
                    color = blau,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable {
                            viewModel.sendVerificationCode()
                        }
                    ,
                )
                if(state.loading || sendVerificationCodeState.loading){
                    CircularProgressIndicator(
                        color = blau,
                        modifier = Modifier.size(25.dp)
                    )
                }

                if(state.error != null || sendVerificationCodeState.error != null){
                    val text = state.error ?: sendVerificationCodeState.error!!
                    Text(
                        text = text,
                        color = rot,
                        fontSize = 13.sp
                    )
                }

                if(state.successful){
                    LaunchedEffect(true){
                        navController.navigate(ScreenRoute.NameScreen.route){
                            popUpTo(ScreenRoute.ValidationScreen.route){inclusive = true}
                        }
                    }
                }
                LaunchedEffect(sendVerificationCodeState.successful){
                    if(sendVerificationCodeState.successful){
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = sendVerificationCodeMessage
                        )
                    }
                }
            }
            AuthButton(
                text = stringResource(id = R.string.next),
                backgroundColor = dunkelgrau,
                contentColor = weiss,
                fontSize = 17,
                enabled = viewModel.validateEmailState.value.buttonEnabled,
                onButtonClick = {
                    viewModel.validateEmail()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 15.dp)
                    .height(45.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }

}