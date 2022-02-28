package kerasinoapps.kapps.infona.auth_module.presentation.composables

import android.graphics.drawable.Icon
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardHide
import androidx.compose.material.icons.filled.PanoramaFishEye
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.Placeholder
import kerasinoapps.kapps.infona.ui.theme.InfonaTheme
import kerasinoapps.kapps.infona.ui.theme.blau
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun TextEntryModul(
    text:String,
    hint:String,
    imageVector: ImageVector,
    value:String,
    keyboardType:KeyboardType = KeyboardType.Ascii,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,
    textColor:Color,
    cursorColor:Color,
    textSize:Int,
    textFieldSize:Int,
    onValueChanged:(String)->Unit,
    trailingIcon:ImageVector? = null,
    onTrailingIconClick:()->Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start,
            color = textColor,
            fontSize = textSize.sp
        )
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(0.5.dp, textColor, RoundedCornerShape(25.dp))
                .height(50.dp)
                .shadow(3.dp, RoundedCornerShape(25.dp)),
            value = value,
            backgroundColor = weiss,
            cursorColor = cursorColor,
            textColor = textColor,
            icon = imageVector,
            placeholderText = hint,
            textFieldSize = textFieldSize,
            keyboardType = keyboardType,
            visualTransformation = visualTransformation,
            onValueChanged = onValueChanged,
            trailingIcon = trailingIcon,
            onTrailingIconClick = onTrailingIconClick
        )
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value:String,
    backgroundColor:Color,
    cursorColor: Color,
    textColor: Color,
    icon: ImageVector,
    placeholderText:String,
    textFieldSize: Int,
    textAlign:TextAlign = TextAlign.Start,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation,
    onValueChanged: (String) -> Unit,
    trailingIcon:ImageVector? = null,
    onTrailingIconClick:()->Unit
) {
    TextField(
        modifier = modifier,
        value = value,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor,
            cursorColor = cursorColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = {
            onValueChanged(it)
        },
        shape = RoundedCornerShape(25.dp),
        singleLine = true,
        leadingIcon ={
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = cursorColor
            )
        } ,
        trailingIcon = {
            if(trailingIcon != null){
                Icon(
                    imageVector = Icons.Default.RemoveRedEye,
                    contentDescription = "eye",
                    tint = cursorColor,
                    modifier = Modifier
                        .clickable {
                            onTrailingIconClick()
                        }
                )
            }
        },
        placeholder = { Text (placeholderText, fontSize = textFieldSize.sp, textAlign = textAlign) },
        textStyle = TextStyle(
            color = textColor,
            fontSize = textFieldSize.sp,
            textAlign = textAlign
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation
    )
}
@Composable
fun CustomTextFieldWithoutIcon(
    modifier: Modifier = Modifier,
    value:String,
    backgroundColor:Color,
    cursorColor: Color,
    textColor: Color,
    placeholderText:String,
    textFieldSize: Int,
    textAlign:TextAlign = TextAlign.Start,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation,
    onValueChanged: (String) -> Unit,
    roundedCornerShape:Int = 25
) {
    TextField(
        modifier = modifier,
        value = value,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor,
            cursorColor = cursorColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = {
            onValueChanged(it)
        },
        shape = RoundedCornerShape(roundedCornerShape.dp),
        singleLine = true,
        placeholder = { Text (modifier=Modifier.fillMaxWidth(),text = placeholderText, fontSize = textFieldSize.sp, textAlign = textAlign) },
        textStyle = TextStyle(
            color = textColor,
            fontSize = textFieldSize.sp,
            textAlign = textAlign
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation
    )
}


@Preview(showBackground = true)
@Composable
fun TextEntryModulePreview(){
    InfonaTheme {
       TextEntryModul(
           text = "Email-Adresse",
           modifier = Modifier
               .fillMaxWidth()
               .padding(10.dp,0.dp,10.dp,5.dp),
           hint = "MatthiasKerat@web.de",
           imageVector = Icons.Default.Email,
           value = "",
           textColor = dunkelgrau,
           cursorColor = blau,
           textSize = 13,
           textFieldSize = 14,
           onValueChanged = {},
           trailingIcon = null,
           onTrailingIconClick = {}
       )
    }

}