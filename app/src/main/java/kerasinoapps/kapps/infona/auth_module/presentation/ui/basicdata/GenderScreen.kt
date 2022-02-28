package kerasinoapps.kapps.infona.auth_module.presentation.ui.basicdata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import kerasinoapps.kapps.infona.R
import kerasinoapps.kapps.infona.auth_module.presentation.composables.AuthButton
import kerasinoapps.kapps.infona.auth_module.presentation.composables.GenderPicker
import kerasinoapps.kapps.infona.auth_module.presentation.composables.Header
import kerasinoapps.kapps.infona.auth_module.presentation.composables.ProgressCircleRow
import kerasinoapps.kapps.infona.auth_module.presentation.viewmodel.BasicDataViewModel
import kerasinoapps.kapps.infona.navigation.ScreenRoute
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun GenderScreen(
    viewModel:BasicDataViewModel,
    navController: NavController
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = weiss)
    ) {

        val (headerArea,dots, button) = createRefs()

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxHeight(0.20f)
                .constrainAs(headerArea) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Header(
                text = stringResource(id = R.string.select_your_gender),
                fontSize = 26,
                textColor = dunkelgrau,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        GenderPicker(
            onGenderSelected = {
                 viewModel.onGenderChanged(it)
            },
            selectedGender = viewModel.selectedGender.value,
            modifier = Modifier
                .fillMaxSize()
        )

        ProgressCircleRow(
            fillNumber = 2,
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
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(45.dp)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom, margin = 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            navController.navigate(ScreenRoute.BirthdayScreen.route)
        }
    }
}