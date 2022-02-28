package kerasinoapps.kapps.infona.main_module.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kerasinoapps.kapps.infona.auth_module.presentation.composables.AuthButton
import kerasinoapps.kapps.infona.ui.theme.*

@Composable
fun SubjectCard(
    name:String,
    modifier:Modifier = Modifier,
    color: Color,
    onClick:()->Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(15.dp))
            .background(
                color,
                RoundedCornerShape(15.dp)
            )
            .clickable { onClick() }
            .padding(15.dp, 30.dp, 15.dp, 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = name,
            color = weiss,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "arrow_forward",
            tint = weiss
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SubjectCardPreview() {
    InfonaTheme {
        SubjectCard(
            name = "Theoretische Informatik 1",
            color = rot,
            onClick = {}
        )
    }
}