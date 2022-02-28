package kerasinoapps.kapps.infona.main_module.presentation.composables.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kerasinoapps.kapps.infona.ui.theme.InfonaTheme
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun BackTopBar(
    modifier: Modifier = Modifier,
    onBackClick:()->Unit
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .background(dunkelgrau)
            .fillMaxWidth()
            .padding(13.dp,13.dp,0.dp,13.dp)
    ){
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "statistik_icon",
            tint = weiss,
            modifier = Modifier
                .clickable {
                    onBackClick()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackTopBarPreview() {
    InfonaTheme {
        BackTopBar(onBackClick = {})
    }
}