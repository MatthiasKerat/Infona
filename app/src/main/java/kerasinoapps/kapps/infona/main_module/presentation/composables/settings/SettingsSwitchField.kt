package kerasinoapps.kapps.infona.main_module.presentation.composables.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau

@Composable
fun SettingsSwitchField(
    text:String,
    fontSize:Int,
    modifier:Modifier = Modifier,
    checked:Boolean,
    onToggle:(Boolean)->Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = text,
            fontSize = fontSize.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = dunkelgrau,
                uncheckedThumbColor = dunkelgrau
            )
        )
    }
}