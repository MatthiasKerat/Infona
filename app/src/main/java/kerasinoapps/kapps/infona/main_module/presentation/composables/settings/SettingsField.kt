package kerasinoapps.kapps.infona.main_module.presentation.composables.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun SettingsField(
    textLeft:String,
    textRight:String,
    fontSize:Int,
    modifier:Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = textLeft,
            fontSize = fontSize.sp
        )
        Text(
            text = textRight,
            fontSize = fontSize.sp
        )
    }
}