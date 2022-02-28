package kerasinoapps.kapps.infona.main_module.presentation.composables.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SettingsSection(
    text:String,
    fontSize:Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ){
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp
        )
    }
}