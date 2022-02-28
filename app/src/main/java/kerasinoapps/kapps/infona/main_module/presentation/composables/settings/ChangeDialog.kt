package kerasinoapps.kapps.infona.main_module.presentation.composables.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kerasinoapps.kapps.infona.auth_module.presentation.composables.CustomTextFieldWithoutIcon
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.rot
import kerasinoapps.kapps.infona.ui.theme.total_weiss
import kerasinoapps.kapps.infona.ui.theme.weiss

@ExperimentalComposeUiApi
@Composable
fun ChangeDialog(
    onDismiss:()->Unit,
    text:String,
    hintText:String,
    textValue:String,
    onSave:()->Unit,
    dialogInputError:Boolean,
    onTextValueChanged:(String)->Unit,
    modifier:Modifier = Modifier
) {

    val borderColor: Color = dialogInputError.let { error ->
        if(error) rot else dunkelgrau
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = modifier
        ){
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ){
                Text(
                    text = text,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                CustomTextFieldWithoutIcon(
                    value = textValue,
                    backgroundColor = total_weiss,
                    cursorColor = dunkelgrau,
                    textColor = dunkelgrau,
                    placeholderText = hintText,
                    textFieldSize = 14,
                    keyboardType = KeyboardType.Ascii,
                    textAlign = TextAlign.Center,
                    visualTransformation = VisualTransformation.None,
                    onValueChanged = onTextValueChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(0.5.dp, borderColor, RoundedCornerShape(25.dp))
                        .height(50.dp)
                        .shadow(3.dp, RoundedCornerShape(25.dp)),
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier
                            .weight(1f,true)
                    ) {
                        DialogButton(
                            text = "Cancle",
                            backgroundColor = dunkelgrau,
                            contentColor = weiss,
                            padding = PaddingValues(7.dp),
                            onButtonClick = onDismiss
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f,true)
                    ) {
                        DialogButton(
                            text = "Save",
                            enabled = !dialogInputError,
                            backgroundColor = dunkelgrau,
                            contentColor = weiss,
                            padding = PaddingValues(7.dp),
                            onButtonClick = onSave
                        )
                    }
                }
            }
        }
    }

}