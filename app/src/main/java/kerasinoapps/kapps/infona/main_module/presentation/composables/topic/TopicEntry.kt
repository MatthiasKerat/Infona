package kerasinoapps.kapps.infona.main_module.presentation.composables.topic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun TopicEntry(
    name:String,
    doneExercises:Int,
    exerciseCount:Int,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick:()->Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(12.dp))
            .background(
                weiss,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)){
            Text(
                text = name,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            if(doneExercises==exerciseCount){
                Icon(
                    imageVector = Icons.Default.CheckCircleOutline,
                    contentDescription = "finished",
                    tint = textColor
                )
            }
        }

        Text("${doneExercises}/${exerciseCount}")
    }

}