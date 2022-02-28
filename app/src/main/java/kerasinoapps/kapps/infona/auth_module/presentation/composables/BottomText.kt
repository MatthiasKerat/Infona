package kerasinoapps.kapps.infona.auth_module.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kerasinoapps.kapps.infona.ui.theme.InfonaTheme
import kerasinoapps.kapps.infona.ui.theme.blau
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau

@Composable
fun BottomText(
    text1: String,
    text2: String,
    fontSize:Int,
    colorText1: Color,
    colorText2:Color,
    modifier:Modifier = Modifier,
    onTextClicked:()->Unit
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        Text(
            text = text1,
            color = colorText1,
            fontSize = fontSize.sp
        )
        Text(
            text = text2,
            color = colorText2,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp,
            modifier = Modifier
                .padding(start = 5.dp)
                .clickable {
                    onTextClicked()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomTextPreview() {
    InfonaTheme {
        BottomText(
            text1 = "Already have an account?",
            text2 = "Login",
            fontSize = 13,
            colorText1 = dunkelgrau,
            colorText2 = blau,
            modifier = Modifier
                .padding(0.dp,0.dp,0.dp,10.dp)
        ) {

        }
    }
}