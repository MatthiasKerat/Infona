package kerasinoapps.kapps.infona.main_module.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kerasinoapps.kapps.infona.main_module.presentation.viewmodel.settings.SettingsViewModel
import kerasinoapps.kapps.infona.R
import kerasinoapps.kapps.infona.main_module.presentation.composables.settings.*
import kerasinoapps.kapps.infona.main_module.presentation.composables.settings.enums.DialogToShow
import kerasinoapps.kapps.infona.navigation.ScreenRoute
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.hellblau
import kerasinoapps.kapps.infona.ui.theme.hellgrau
import kerasinoapps.kapps.infona.ui.theme.weiss

@ExperimentalComposeUiApi
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val user = viewModel.user.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            )
    ){
        BackTopBar(
            onBackClick = {
                navController.popBackStack()
            }
        )
        SettingsSection(
            text = stringResource(id = R.string.user_data),
            fontSize = 16,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, top = 20.dp, bottom = 20.dp)
        )
        SettingsLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .shadow(3.dp)
                .background(dunkelgrau)
        )
        SettingsField(
            textLeft = stringResource(id = R.string.name),
            textRight = user.name,
            fontSize = 15,
            modifier = Modifier
                .clickable {
                    viewModel.showEditDialog(DialogToShow.NAME)
                }
                .fillMaxWidth()
                .padding(15.dp, 20.dp, 15.dp, 20.dp)
        )
        SettingsLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(dunkelgrau)
        )
        SettingsField(
            textLeft = stringResource(id = R.string.gender),
            textRight = user.gender,
            fontSize = 15,
            modifier = Modifier
                .clickable {
                    viewModel.showEditDialog(DialogToShow.GENDER)
                }
                .fillMaxWidth()
                .padding(15.dp, 20.dp, 15.dp, 20.dp)
        )
        SettingsLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(dunkelgrau)
        )
        SettingsField(
            textLeft = stringResource(id = R.string.university),
            textRight = user.university,
            fontSize = 15,
            modifier = Modifier
                .clickable {
                    viewModel.showEditDialog(DialogToShow.UNIVERSITY)
                }
                .fillMaxWidth()
                .padding(15.dp, 20.dp, 15.dp, 20.dp)
        )
        SettingsLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(dunkelgrau)
        )
        SettingsField(
            textLeft = stringResource(id = R.string.average_grade),
            textRight = user.averageGrade.toString(),
            fontSize = 15,
            modifier = Modifier
                .clickable {
                    viewModel.showEditDialog(DialogToShow.AVERAGEGRADE)
                }
                .fillMaxWidth()
                .padding(15.dp, 20.dp, 15.dp, 20.dp)
        )
        SettingsLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(dunkelgrau)
        )
        SettingsSection(
            text = stringResource(id = R.string.privacy),
            fontSize = 16,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, top = 20.dp, bottom = 20.dp)
        )
        SettingsLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .shadow(3.dp)
                .background(dunkelgrau)
        )
        SettingsSwitchField(
            text = stringResource(id = R.string.show_email),
            fontSize = 15,
            checked = user.privacy.get(0),
            onToggle = {
                viewModel.onShowEmailAddressToggle()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 20.dp, 15.dp, 20.dp)
        )
        SettingsLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(dunkelgrau)
        )
        SettingsSwitchField(
            text = stringResource(id = R.string.show_average_grade),
            fontSize = 15,
            checked = user.privacy.get(1),
            onToggle = {
                viewModel.onShowAverageGradeToggle()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 20.dp, 15.dp, 20.dp)
        )
        SettingsLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(dunkelgrau)
        )
        SettingsSwitchField(
            text = stringResource(id = R.string.show_age),
            fontSize = 15,
            checked = user.privacy.get(2),
            onToggle = {
                viewModel.onShowAgeToggle()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 20.dp, 15.dp, 20.dp)
        )
        SettingsLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(dunkelgrau)
        )
        Text(
            text = "Logout",
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable{
                    viewModel.onLogout()
                    navController.navigate(ScreenRoute.LoginScreen.route){
                        popUpTo(ScreenRoute.MainMenuScreen.route){inclusive = true}
                    }
                }
                .padding(15.dp),
            textAlign = TextAlign.Center
        )
        SettingsLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(dunkelgrau)
        )
        Text(
            text = stringResource(id = R.string.version),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            color = hellgrau
        )

        if(viewModel.showEditDialog.value){
            when(viewModel.dialogToShow.value){
                DialogToShow.NAME -> {
                    ChangeDialog(
                        onDismiss = {
                            viewModel.dismissDialog()
                        },
                        text = stringResource(id = R.string.new_name),
                        dialogInputError = viewModel.showDialogError.value,
                        hintText = stringResource(id = R.string.your_name),
                        textValue = viewModel.dialogNameInput.value,
                        onSave = {
                            viewModel.onNameChanged()
                            viewModel.dismissDialog()
                        },
                        onTextValueChanged = {
                             viewModel.onNameInputChanged(it)
                        },
                        modifier = Modifier.fillMaxWidth(0.95f)
                    )
                }
                DialogToShow.GENDER->{
                    ChangeDialog(
                        onDismiss = {
                            viewModel.dismissDialog()
                        },
                        text = stringResource(id = R.string.new_gender),
                        dialogInputError = viewModel.showDialogError.value,
                        hintText = stringResource(id = R.string.your_gender),
                        textValue = viewModel.dialogGenderInput.value,
                        onSave = {
                            viewModel.onGenderChanged()
                            viewModel.dismissDialog()
                        },
                        onTextValueChanged = {
                            viewModel.onGenderInputChanged(it)
                        },
                        modifier = Modifier.fillMaxWidth(0.95f)
                    )
                }
                DialogToShow.UNIVERSITY ->{
                    ChangeDialog(
                        onDismiss = {
                            viewModel.dismissDialog()
                        },
                        text = stringResource(id = R.string.new_university),
                        dialogInputError = viewModel.showDialogError.value,
                        hintText = stringResource(id = R.string.your_university),
                        textValue = viewModel.dialogUniversityInput.value,
                        onSave = {
                            viewModel.onUniversityChanged()
                            viewModel.dismissDialog()
                        },
                        onTextValueChanged = {
                            viewModel.onUniversityInputChanged(it)
                        },
                        modifier = Modifier.fillMaxWidth(0.95f)
                    )
                }
                DialogToShow.AVERAGEGRADE ->{
                    ChangeDialog(
                        onDismiss = {
                            viewModel.dismissDialog()
                        },
                        text = stringResource(id = R.string.new_average_grade),
                        dialogInputError = viewModel.showDialogError.value,
                        hintText = stringResource(id = R.string.your_average_grade),
                        textValue = viewModel.dialogAverageGradeInput.value,
                        onSave = {
                            viewModel.onAverageGradeChanged()
                            viewModel.dismissDialog()
                        },
                        onTextValueChanged = {
                            viewModel.onAverageGradeInputChanged(it)
                        },
                        modifier = Modifier.fillMaxWidth(0.95f)
                    )
                }
            }
        }

    }

}