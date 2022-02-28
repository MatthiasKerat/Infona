package kerasinoapps.kapps.infona.main_module.presentation.composables.exercise

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.rotationMatrix
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Tipp
import kerasinoapps.kapps.infona.main_module.presentation.composables.SubjectCard
import kerasinoapps.kapps.infona.ui.theme.InfonaTheme
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.rot
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun TippCard(
    modifier:Modifier = Modifier,
    tipp: Tipp,
    tippNumber:String,
    alreadyBought:Boolean,
    onClick:()->Unit,
    onClickToBuy:()->Unit,
    isExpaned:Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .background(
                weiss,
                RoundedCornerShape(10.dp)
            )
            .border(1.dp, color = dunkelgrau, RoundedCornerShape(10.dp))
            .clickable {
                if(alreadyBought){
                    onClick()
                }else {
                    onClickToBuy()
                }
            }
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = tippNumber,
                color = dunkelgrau,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            if(alreadyBought){
                if(isExpaned){
                    Icon(
                        imageVector = Icons.Default.ArrowDropUp,
                        contentDescription = "arrow down"
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "arrow down"
                    )
                }


            }else{
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "lock",
                        tint = dunkelgrau
                    )
                    Text(
                        text = "${tipp.coinCost} IC$",
                        fontSize = 14.sp,
                        color = dunkelgrau
                    )
                }
            }
        }
        if(isExpaned){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(dunkelgrau)
            )
            Text(
                text = tipp.description,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SubjectCardPreview() {
    InfonaTheme {
        TippCard(
            tipp = Tipp("001",50,"Dieser Tipp gibt einen Hinweise auf die Fragestellung. Durch die Info dieses Tips kann die Frage vllt beantwortet werden."),
            alreadyBought = true,
            tippNumber = "Tipp 1",
            onClick = {},
            isExpaned = false,
            onClickToBuy = {}
        )
    }
}