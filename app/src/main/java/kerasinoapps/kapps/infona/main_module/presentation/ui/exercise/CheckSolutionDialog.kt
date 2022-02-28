package kerasinoapps.kapps.infona.main_module.presentation.ui.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kerasinoapps.kapps.infona.R
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Tipp
import kerasinoapps.kapps.infona.main_module.presentation.composables.exercise.TippCard
import kerasinoapps.kapps.infona.main_module.presentation.composables.settings.DialogButton
import kerasinoapps.kapps.infona.ui.theme.InfonaTheme
import kerasinoapps.kapps.infona.ui.theme.blau

@ExperimentalComposeUiApi
@Composable
fun CheckSolutionDialog(
    onDismiss:()->Unit,
    onSubmit:()->Unit,
) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ){
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ){
            Column(
               modifier = Modifier
                   .padding(15.dp)
                   .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ){
                Text(
                    text = stringResource(id = R.string.antwort_pruefen),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier
                            .weight(1f,true)
                    ) {
                        DialogButton(
                            text = stringResource(id = R.string.abbrechen),
                            backgroundColor = blau,
                            contentColor = colorResource(id = R.color.white),
                            padding = PaddingValues(7.dp),
                            onButtonClick = onDismiss
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f,true)
                    ) {
                        DialogButton(
                            text = stringResource(id = R.string.speichern),
                            backgroundColor = blau,
                            contentColor = colorResource(id = R.color.white),
                            padding = PaddingValues(7.dp),
                            onButtonClick = onSubmit
                        )
                    }
                }
            }
        }
    }

}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun CheckSolutionDialogPreview() {
    InfonaTheme {
        CheckSolutionDialog(
            onDismiss = {},
            onSubmit = {}
        )
    }
}