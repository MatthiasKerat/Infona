package kerasinoapps.kapps.infona.auth_module.presentation.styles

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class StyleDatePicker(
    val scaleWidth: Dp = 60.dp,
    val radius: Dp = 550.dp,
    val lineColor: Color,
    val lineLength: Float = 35f,
    val scaleIndicatorColor: Color,
    val scaleIndicatorLength: Dp = 15.dp,
    val textSize: TextUnit = 16.sp
)