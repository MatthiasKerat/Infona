package kerasinoapps.kapps.infona.auth_module.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kerasinoapps.kapps.infona.ui.theme.InfonaTheme
import kerasinoapps.kapps.infona.ui.theme.blau
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun VerificationInputField(
    value:String,
    modifier:Modifier = Modifier,
    textColor:Color,
    textFieldSize:Int,
    onTextChanged:(String)->Unit
) {

    BasicTextField(
        value = value,
        onValueChange = onTextChanged,
        modifier = modifier,
        textStyle = TextStyle(
            color = textColor,
            fontSize = textFieldSize.sp,
            textAlign = TextAlign.Center
        ),
        cursorBrush = SolidColor(Color.Transparent)
    )

    /*BasicTextField(
        value = value,
        onValueChange = onTextChanged,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = fieldColor,
            textColor = textColor,
            cursorColor = Color.Transparent
        ),
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        textStyle = TextStyle(
            color = textColor,
            fontSize = textFieldSize.sp,
            textAlign = TextAlign.Center
        )
    )*/

}

@Preview(showBackground = true)
@Composable
fun VerificationTextPreview() {
    InfonaTheme {
        VerificationInputField(
            value = "5",
            textColor = dunkelgrau,
            textFieldSize = 35,
            onTextChanged = {},
            modifier = Modifier
                .border(1.dp, color = dunkelgrau, RoundedCornerShape(20.dp))
                .shadow(3.dp, RoundedCornerShape(20.dp))
                .width(Dp(50f))
                .aspectRatio(0.9f)
                .background(color = weiss)
        )
    }
}