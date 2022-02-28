package kerasinoapps.kapps.infona.auth_module.presentation.ui.basicdata

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kerasinoapps.kapps.infona.R
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import kerasinoapps.kapps.infona.auth_module.presentation.composables.*
import kerasinoapps.kapps.infona.auth_module.presentation.viewmodel.BasicDataViewModel
import kerasinoapps.kapps.infona.navigation.ScreenRoute
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.total_weiss
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun NameScreen(
    viewModel: BasicDataViewModel,
    navController: NavController
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = weiss)
    ) {

        val (button,dots,nameArea) = createRefs()

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.35f)
                .constrainAs(nameArea) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Header(
                    text = stringResource(id = R.string.enter_your_name),
                    fontSize = 26,
                    textColor = dunkelgrau,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                CustomTextFieldWithoutIcon(
                    value = viewModel.nameInput.value,
                    backgroundColor = total_weiss,
                    cursorColor = dunkelgrau,
                    textColor = dunkelgrau,
                    placeholderText = "Matthias",
                    textFieldSize = 14,
                    textAlign = TextAlign.Center,
                    keyboardType = KeyboardType.Ascii,
                    visualTransformation = VisualTransformation.None,
                    onValueChanged = {
                        viewModel.onNameChanged(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, dunkelgrau, RoundedCornerShape(25.dp))
                        .height(50.dp)
                        .shadow(3.dp, RoundedCornerShape(25.dp)),
                )
            }

        }
        ProgressCircleRow(
            fillNumber = 1,
            modifier = Modifier
                .constrainAs(dots){
                    bottom.linkTo(button.top, margin = 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

        )
        AuthButton(
            text = stringResource(id = R.string.next),
            backgroundColor = dunkelgrau,
            contentColor = weiss,
            fontSize = 16,
            enabled = viewModel.nameInput.value.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(45.dp)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom, margin = 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            navController.navigate(ScreenRoute.GenderScreen.route)
        }
    }
}