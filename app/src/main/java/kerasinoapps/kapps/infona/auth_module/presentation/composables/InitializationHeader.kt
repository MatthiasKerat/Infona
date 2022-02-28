package kerasinoapps.kapps.infona.auth_module.presentation.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun InitializationHeader(
    text: String,
    modifier:Modifier = Modifier,
    fontSize: Int,
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Bold
    )
}