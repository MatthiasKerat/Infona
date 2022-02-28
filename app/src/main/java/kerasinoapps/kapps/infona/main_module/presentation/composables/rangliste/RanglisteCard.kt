package kerasinoapps.kapps.infona.main_module.presentation.composables.rangliste

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.main_module.presentation.composables.SubjectCard
import kerasinoapps.kapps.infona.ui.theme.InfonaTheme
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.rot
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun RanglisteCard(
    user: User,
    modifier: Modifier = Modifier,
    borderColor: Color,
    onClick:()->Unit,
    picture:ImageBitmap? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(15.dp))
            .background(
                weiss,
                RoundedCornerShape(15.dp)
            )
            .border(2.dp, color = borderColor, RoundedCornerShape(15.dp))
            .clickable { onClick() }
            .padding(13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .background(weiss, shape = CircleShape)
                    .clip(CircleShape)
                    .border(1.5.dp,color = dunkelgrau,shape = CircleShape)
                    .size(50.dp)
            ){
                if(picture!=null){
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        bitmap = picture,
                        contentDescription = "profile_picture"
                    )
                }
            }
            Column(

            ){
                Text(
                    text = user.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user.university,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Text(
            text = "${user.coins} IC$",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }
}

