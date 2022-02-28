package kerasinoapps.kapps.infona.main_module.presentation.composables.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DialogButton(
    text: String,
    backgroundColor: Color,
    enabled:Boolean = true,
    contentColor: Color,
    padding: PaddingValues = PaddingValues(0.dp,0.dp,0.dp,0.dp),
    onButtonClick:()->Unit
) {
    Button(
        onClick = {
            onButtonClick()
        },
        enabled=enabled,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues = padding)
            .height(40.dp)
            .shadow(5.dp, RoundedCornerShape(20.dp)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )

    ) {
        Text(text, fontSize = 15.sp)
    }
}