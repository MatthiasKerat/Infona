package kerasinoapps.kapps.infona.main_module.presentation.composables.exercise

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun RoundButton(
    iconColor: Color,
    modifier:Modifier = Modifier,
    imageVector:ImageVector? = null,
    text:String? = null,
    onButtonClick:()->Unit,
    size:Int
) {

    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
    ){
        Box(
            modifier = modifier
                .size(size.dp)
                .shadow(5.dp, CircleShape)
                .clip(CircleShape)
                .background(weiss)
                .clickable {
                    onButtonClick()
                }
            ,
            contentAlignment = Alignment.Center
        ){
            if(imageVector!=null){
                Icon(
                    imageVector = imageVector,
                    contentDescription = "action",
                    tint = iconColor,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
            if(text!=null){
                Text(
                    text = text,
                    fontSize = 17.sp,
                    color = iconColor,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }

}

