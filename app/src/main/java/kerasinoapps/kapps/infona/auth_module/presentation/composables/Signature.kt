package kerasinoapps.kapps.infona.auth_module.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kerasinoapps.kapps.infona.ui.theme.InfonaTheme
import kerasinoapps.kapps.infona.ui.theme.blau
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau

@Composable
fun Signature(
    text1:String,
    text2:String,
    text3:String,
    primaryColor: Color,
    secondaryColor: Color,
    primaryFontSize:Int,
    secondaryFontSize:Int,
    modifier:Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            Text(
                text1,
                color = primaryColor,
                fontSize = primaryFontSize.sp
            )
            Text(
                text2,
                color = secondaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = primaryFontSize.sp
            )
        }
        Text(
            text3,
            color = primaryColor,
            fontSize = secondaryFontSize.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignaturePreview(){
    InfonaTheme {
        Signature(
            text1 = "Created",
            text2 = "by",
            text3 = "Matthias Kerat",
            modifier = Modifier
                .fillMaxWidth(0.4f),
            primaryColor = dunkelgrau,
            secondaryColor = blau,
            primaryFontSize = 13,
            secondaryFontSize = 10
        )
    }

}