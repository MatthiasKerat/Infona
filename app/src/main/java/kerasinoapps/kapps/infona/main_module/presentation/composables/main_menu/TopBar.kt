package kerasinoapps.kapps.infona.main_module.presentation.composables.main_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.MultilineChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kerasinoapps.kapps.infona.main_module.presentation.composables.SubjectCard
import kerasinoapps.kapps.infona.ui.theme.InfonaTheme
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.rot
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun TopBar(
    letter:String,
    modifier:Modifier = Modifier,
    onSettingsClick:()->Unit,
    onRanglisteClick:()->Unit,
    onInitializationLetterClick:()->Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(dunkelgrau)
            .fillMaxWidth()
            .padding(8.dp,8.dp,8.dp,8.dp)
    ){
        Initializer(letter = letter, onInitializationLetterClick = onInitializationLetterClick)
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ){
            Icon(
                imageVector = Icons.Default.BarChart,
                contentDescription = "statistik_icon",
                tint = weiss,
                modifier = Modifier
                    .clickable {
                        onRanglisteClick()
                    }
            )
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "settings_icon",
                tint = weiss,
                modifier = Modifier
                    .clickable {
                        onSettingsClick()
                    }
            )
        }
    }
}

@Composable
fun Initializer(
    letter:String,
    onInitializationLetterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(weiss, CircleShape)
            .padding(5.dp)
            .size(25.dp)
            .clickable {
                onInitializationLetterClick()
            },
        contentAlignment = Alignment.Center
    ){
        Text(
            text = letter,
            fontWeight = FontWeight.Bold,
            color = dunkelgrau,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    InfonaTheme {
        TopBar(letter = "M", onSettingsClick = {}, onRanglisteClick = {}, onInitializationLetterClick = {})
    }
}
