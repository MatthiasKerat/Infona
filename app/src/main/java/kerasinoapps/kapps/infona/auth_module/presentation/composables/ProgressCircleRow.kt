package kerasinoapps.kapps.infona.auth_module.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import kerasinoapps.kapps.infona.ui.theme.blau
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun ProgressCircleRow(
    fillNumber:Int,
    modifier:Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier
    ) {
        for(i in 0..4){
            if(i+1 == fillNumber){
                ProgressCircle(isFilled = true)
            }else{
                ProgressCircle(isFilled = false)
            }
        }
    }
}

@Composable
fun ProgressCircle(
    fillColor : Color = blau,
    defaultColor : Color = weiss,
    isFilled: Boolean
) {
    Canvas(modifier = Modifier
        .width(15.dp)
        .height(15.dp)
        .shadow(2.dp, RoundedCornerShape(25.dp))
    ){
        drawCircle(
            radius = if(isFilled)25f else 23f,
            color = if(isFilled) fillColor else defaultColor
        )
    }
}