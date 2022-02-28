package kerasinoapps.kapps.infona.auth_module.presentation.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kerasinoapps.kapps.infona.R

@Composable
fun GenderPicker(
    modifier: Modifier = Modifier,
    maleGradient: List<Color> = listOf(Color(0xFF6D6DFF), Color.Blue),
    femaleGradient: List<Color> = listOf(Color(0xFFEA76FF), Color.Magenta),
    distanceBetweenGenders: Dp = 50.dp,
    pathScaleFactor: Float = 7f,
    onGenderSelected: (String)->Unit,
    selectedGender: String
) {
    var center by remember {
        mutableStateOf(Offset.Unspecified)
    }

    var selectedGenderInter by remember{
        mutableStateOf(selectedGender)
    }

    val malePathString = stringResource(id = R.string.male_path)
    val femalePathString = stringResource(id = R.string.female_path)

    val malePath = remember {
        PathParser().parsePathString(malePathString).toPath()
    }

    val femalePath = remember{
        PathParser().parsePathString(femalePathString).toPath()
    }

    val malePathBounds = remember{
        malePath.getBounds()
    }

    val femalePathBounds = remember{
        femalePath.getBounds()
    }
    var maleTranslationOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    var femaleTranslationOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    var currentClickOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    val maleSelectionRadius = animateFloatAsState(
        targetValue = if(selectedGenderInter == "Male") 80f else 0f,
        animationSpec = tween(durationMillis = 500)
    )
    val femaleSelectionRadius = animateFloatAsState(
        targetValue = if(selectedGenderInter == "Female") 80f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Canvas(
        modifier = modifier
            .pointerInput(true){
                detectTapGestures {
                    val transformedMaleRect = Rect(
                        offset = maleTranslationOffset,
                        size = malePathBounds.size * pathScaleFactor
                    )
                    val transformedFemaleRect = Rect(
                        offset = femaleTranslationOffset,
                        size = femalePathBounds.size * pathScaleFactor
                    )
                    if(selectedGenderInter != "Male" && transformedMaleRect.contains(it)){
                        currentClickOffset = it
                        selectedGenderInter = "Male"
                        onGenderSelected("Male")
                    }else if(selectedGenderInter != "Female" && transformedFemaleRect.contains(it)){
                        currentClickOffset = it
                        selectedGenderInter = "Female"
                        onGenderSelected("Female")
                    }
                }
            }
    ){
        center = this.center

        maleTranslationOffset = Offset(
            x = center.x - malePathBounds.width * pathScaleFactor - distanceBetweenGenders.toPx() / 2f,
            y = center.y - malePathBounds.height * pathScaleFactor / 2f
        )
        femaleTranslationOffset = Offset(
            x = center.x + distanceBetweenGenders.toPx() / 2f,
            y = center.y - femalePathBounds.height * pathScaleFactor / 2f
        )

        val untransformedMaleClickOffset = if(currentClickOffset == Offset.Zero){
            malePathBounds.center
        }else {
            (currentClickOffset-maleTranslationOffset) / pathScaleFactor
        }
        val untransformedFemaleClickOffset = if(currentClickOffset == Offset.Zero){
            femalePathBounds.center
        }else {
            (currentClickOffset-femaleTranslationOffset) / pathScaleFactor
        }


        translate(
            left = maleTranslationOffset.x,
            top = maleTranslationOffset.y
        ){
            scale(scale = pathScaleFactor,pivot = malePathBounds.topLeft){
                drawPath(
                    path = malePath,
                    color = Color.LightGray
                )
                clipPath(
                    path = malePath
                ){
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = maleGradient,
                            center = untransformedMaleClickOffset,
                            radius = maleSelectionRadius.value + 1
                        ),
                        radius = maleSelectionRadius.value,
                        center = untransformedMaleClickOffset
                    )
                }
            }

        }
        translate(
            left = femaleTranslationOffset.x,
            top = femaleTranslationOffset.y
        ){
            scale(scale = pathScaleFactor,pivot = femalePathBounds.topLeft){
                drawPath(
                    path = femalePath,
                    color = Color.LightGray
                )
                clipPath(
                    path = femalePath
                ){
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = femaleGradient,
                            center = untransformedFemaleClickOffset,
                            radius = femaleSelectionRadius.value + 1
                        ),
                        radius = femaleSelectionRadius.value,
                        center = untransformedFemaleClickOffset
                    )
                }
            }

        }

    }
}