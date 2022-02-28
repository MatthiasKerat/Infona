package kerasinoapps.kapps.infona.auth_module.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kerasinoapps.kapps.infona.ui.theme.*

@Composable
fun HeaderBackground(
    topColor:Color,
    bottomColor:Color,
    modifier: Modifier = Modifier
){
    val colorList = listOf(topColor, bottomColor)
    Canvas(
        modifier = modifier
    ){
        drawCircle(
            radius = 0.5f*size.height,
            center = Offset(center.x,-size.height*0.35f),
            brush = Brush.linearGradient(colorList,end = Offset(center.x+500f,0f))
        )
    }
}

@Composable
fun Header(
    text:String,
    fontSize:Int,
    modifier:Modifier = Modifier,
    textColor: Color
) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        modifier = modifier,
        textAlign = TextAlign.Center,
        color = textColor
    )
}

@Preview(showBackground = true)
@Composable
fun HeaderBackgroundPreview() {
    InfonaTheme {
        Box(){
            HeaderBackground(
                topColor = dunkelgrau,
                bottomColor = hellblau,
                modifier = Modifier.fillMaxSize()
            )
            Header(
                text = "Infona",
                fontSize = 23,
                modifier = Modifier.fillMaxWidth().padding(0.dp,35.dp,0.dp,0.dp),
                textColor = weiss
            )
        }

    }
}