package kerasinoapps.kapps.infona.auth_module.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextTip(
    text:String
) {
    Text(
        text = text,
        modifier = Modifier
            .padding(10.dp,0.dp,0.dp,0.dp),
        fontSize = 11.sp
    )
}