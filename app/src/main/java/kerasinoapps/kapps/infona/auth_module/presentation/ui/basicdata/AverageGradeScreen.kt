package kerasinoapps.kapps.infona.auth_module.presentation.ui.basicdata

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.withRotation
import androidx.navigation.NavController
import kerasinoapps.kapps.infona.R
import kerasinoapps.kapps.infona.auth_module.presentation.composables.AuthButton
import kerasinoapps.kapps.infona.auth_module.presentation.composables.Header
import kerasinoapps.kapps.infona.auth_module.presentation.composables.ProgressCircleRow
import kerasinoapps.kapps.infona.auth_module.presentation.styles.LineType
import kerasinoapps.kapps.infona.auth_module.presentation.styles.StyleAverageGradePicker
import kerasinoapps.kapps.infona.auth_module.presentation.viewmodel.BasicDataViewModel
import kerasinoapps.kapps.infona.navigation.ScreenRoute
import kerasinoapps.kapps.infona.ui.theme.blau
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.weiss
import kotlin.math.*

@Composable
fun AverageGradeScreen(
    viewModel: BasicDataViewModel,
    navController: NavController
) {

    val selectedGrade = viewModel.selectedGrade.value

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = weiss)
    ) {

        val (headerArea,dots,gradePickerSection,button) = createRefs()

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxHeight(0.20f)
                .constrainAs(headerArea) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Header(
                text = stringResource(id = R.string.select_your_average_grade),
                fontSize = 26,
                textColor = dunkelgrau,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .background(color = weiss)
            .constrainAs(gradePickerSection) {
                top.linkTo(headerArea.bottom)
            }
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.padding(0.dp,130.dp,0.dp,0.dp))
            AverageGradeText(grade = selectedGrade)
            Spacer(modifier = Modifier.padding(0.dp,30.dp,0.dp,0.dp))
            AverageGradePicker(
                style = StyleAverageGradePicker(
                    scaleWidth = 150.dp,
                    fiveStepLineColor = blau,
                    scaleIndicatorColor = blau
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ){
                viewModel.onGradeChanged((it/10f).toString())
            }
        }

        ProgressCircleRow(
            fillNumber = 5,
            modifier = Modifier
                .constrainAs(dots){
                    bottom.linkTo(button.top, margin = 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

        )
        AuthButton(
            text = stringResource(id = R.string.next),
            backgroundColor = dunkelgrau,
            contentColor = weiss,
            fontSize = 16,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(45.dp)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom, margin = 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            viewModel.saveUserData()
        }
    }

    if(viewModel.continueToMainMenu.value){
        LaunchedEffect(key1 = true){
            navController.navigate(ScreenRoute.MainMenuScreen.route){
                popUpTo(ScreenRoute.ValidationScreen.route){inclusive = true}
            }
        }
    }


}

@Composable
fun AverageGradePicker(
    modifier: Modifier = Modifier,
    style: StyleAverageGradePicker,
    minWeight: Int = 10,
    maxWeight: Int = 60,
    initialWeight: Int = 30,
    onWeightChange: (Int) -> Unit
) {

    val radius = style.radius
    val scaleWidth = style.scaleWidth
    var center by remember{
        mutableStateOf(Offset.Zero)
    }
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var angle by remember {
        mutableStateOf(0f )
    }

    var dragStartedAngle by remember{
        mutableStateOf(0f)
    }

    var oldAngle by remember {
        mutableStateOf(angle)
    }

    Canvas(
        modifier = modifier
            .pointerInput(true){
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStartedAngle = -atan2(
                            y = circleCenter.x - offset.x,
                            x = circleCenter.y - offset.y
                        ) *(180f/ PI).toFloat()
                    },
                    onDragEnd = {
                        oldAngle = angle
                    }
                ) { change, _ ->
                    val touchAngle = -atan2(
                        y = circleCenter.x - change.position.x,
                        x = circleCenter.y - change.position.y
                    ) *(180f/ PI).toFloat()

                    val newAngle = oldAngle + (touchAngle - dragStartedAngle.toFloat())
                    angle = newAngle.coerceIn(
                        minimumValue = initialWeight - maxWeight.toFloat(),
                        maximumValue = initialWeight - minWeight.toFloat()
                    )
                    onWeightChange((initialWeight-angle).roundToInt())
                }
            }
    ){
        center = this.center
        circleCenter = Offset(center.x, scaleWidth.toPx()/2f + radius.toPx())

        val outerRadius = radius.toPx() + scaleWidth.toPx() / 2f
        val innerRadius = radius.toPx() - scaleWidth.toPx() / 2f

        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                circleCenter.x,
                circleCenter.y,
                radius.toPx(),
                Paint().apply {
                    strokeWidth = scaleWidth.toPx()
                    color = Color.WHITE
                    setStyle(
                        Paint.Style.STROKE
                    )
                    setShadowLayer(
                        60f,
                        0f,
                        0f,
                        Color.argb(50,0,0,0)
                    )
                }
            )
        }

        //Draw lines
        for (i in minWeight..maxWeight){
            val angleInRad = (i - initialWeight + angle - 90) * (PI /180f).toFloat()
            val lineType = when{
                i % 10 == 0 -> LineType.TenStep
                i % 5 == 0 -> LineType.FiveStep
                else -> LineType.Normal
            }
            val lineLength = when(lineType){
                LineType.Normal -> style.normalLineLength.toPx()
                LineType.FiveStep -> style.fiveStepLineLength.toPx()
                LineType.TenStep -> style.tenStepLineLength.toPx()
            }
            val lineColor = when(lineType){
                LineType.Normal -> style.normalLineColor
                LineType.FiveStep -> style.fiveStepLineColor
                LineType.TenStep -> style.tenStepLineColor
            }
            val lineStart = Offset(
                x = (outerRadius - lineLength)* cos(angleInRad) + circleCenter.x,
                y = (outerRadius - lineLength)* sin(angleInRad) + circleCenter.y
            )
            val lineEnd = Offset(
                x = (outerRadius)* cos(angleInRad) + circleCenter.x,
                y = (outerRadius)* sin(angleInRad) + circleCenter.y
            )

            drawContext.canvas.nativeCanvas.apply {
                if(lineType is LineType.TenStep){
                    val textRadius = outerRadius - lineLength - 5.dp.toPx() - style.textSize.toPx()
                    val x = textRadius * cos(angleInRad) + circleCenter.x
                    val y = textRadius * sin(angleInRad) + circleCenter.y
                    withRotation(
                        degrees = angleInRad * 180 / PI.toFloat() + 90f,
                        pivotX = x,
                        pivotY = y
                    ) {
                        drawText(
                            (abs(i)/10f).toString(),
                            x,
                            y,
                            Paint().apply {
                                textSize = style.textSize.toPx()
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }

                }
            }

            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )
            val middleTop = Offset(
                x = circleCenter.x,
                y = circleCenter.y - innerRadius - style.scaleIndicatorLength.toPx()
            )
            val bottomLeft = Offset(
                x = circleCenter.x - 4f,
                y = circleCenter.y - innerRadius
            )

            val bottomRight = Offset(
                x = circleCenter.x + 4f,
                y = circleCenter.y - innerRadius
            )

            val indicator = Path().apply {
                moveTo(middleTop.x, middleTop.y)
                lineTo(bottomLeft.x, bottomLeft.y)
                lineTo(bottomRight.x,bottomRight.y)
                lineTo(middleTop.x, middleTop.y)
            }
            drawPath(
                path = indicator,
                color = style.scaleIndicatorColor
            )
        }

    }

}

@Composable
fun AverageGradeText(
    grade: String
) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = androidx.compose.ui.graphics.Color.Black, fontSize = 40.sp)){
                append(grade)
            }
        }
    )
}