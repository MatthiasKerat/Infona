package kerasinoapps.kapps.infona.auth_module.presentation.ui.basicdata

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import kerasinoapps.kapps.infona.R
import kerasinoapps.kapps.infona.auth_module.presentation.composables.AuthButton
import kerasinoapps.kapps.infona.auth_module.presentation.composables.GenderPicker
import kerasinoapps.kapps.infona.auth_module.presentation.composables.Header
import kerasinoapps.kapps.infona.auth_module.presentation.composables.ProgressCircleRow
import kerasinoapps.kapps.infona.auth_module.presentation.styles.StyleDatePicker
import kerasinoapps.kapps.infona.auth_module.presentation.viewmodel.BasicDataViewModel
import kerasinoapps.kapps.infona.navigation.ScreenRoute
import kerasinoapps.kapps.infona.ui.theme.blau
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.weiss
import kotlin.math.abs
import kotlin.math.round

@Composable
fun BirthdayScreen(
    viewModel: BasicDataViewModel,
    navController: NavController
) {

    val day = viewModel.selectedDay.value
    val month = viewModel.selectedMonth.value
    val year = viewModel.selectedYear.value

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = weiss)
    ) {

        val (headerArea,dots,datePickerSection,button) = createRefs()

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
                text = stringResource(id = R.string.date_of_birth),
                fontSize = 26,
                textColor = dunkelgrau,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(datePickerSection){
                    top.linkTo(headerArea.bottom, margin = 30.dp)
                },
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            DatePickerDay(){
                viewModel.onDayChanged("$it")
            }
            DatePickerMonth(){
                viewModel.onMonthChanged("$it")
            }
            DatePickerYear(){
                viewModel.onYearChanged("$it")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            BirtdayText(day,month,year)
        }
        RectAngleBirthday()

        ProgressCircleRow(
            fillNumber = 3,
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
            navController.navigate(ScreenRoute.UniversityScreen.route)
        }
    }
}

@Composable
fun DatePickerDay(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(60.dp),
    style: StyleDatePicker = StyleDatePicker(lineColor = blau,scaleIndicatorColor = blau),
    onDayChanged: (Int)->Unit
) {
    val scaleWidth = style.scaleWidth
    var dragStartedX by remember{
        mutableStateOf(0f)
    }

    var currentDragX by remember{
        mutableStateOf(0f)
    }

    var oldX by remember{
        mutableStateOf(currentDragX)
    }
    Canvas(modifier = modifier
        .pointerInput(true){
            detectDragGestures(
                onDragStart = { offset->
                    dragStartedX = offset.x
                },
                onDragEnd = {
                    val rest = currentDragX % (size.width/10f)
                    val roundUp = round(abs(rest/(size.width/10f))).toInt() == 1
                    var newX = 0f
                    if(roundUp){
                        if(rest<0){
                            newX = currentDragX + abs(rest) - size.width/10f
                        }else{
                            newX = currentDragX - rest + size.width/10f
                        }
                    }else{
                        if(rest<0){
                            newX = currentDragX + abs(rest)
                        }else{
                            newX = currentDragX - rest
                        }
                    }

                    currentDragX = newX.coerceIn(
                        minimumValue = -14f*(size.width/10f),
                        maximumValue = 16f*(size.width/10f))
                    val newDay= 15+(currentDragX/(size.width/10f)).toInt()
                    onDayChanged(newDay)
                    oldX = currentDragX
                }
            ) { change, _ ->
                val changeX = change.position.x
                var newX = oldX + (dragStartedX-changeX)

                currentDragX = newX.coerceIn(
                    minimumValue = -14f*(size.width/10f),
                    maximumValue = 16f*(size.width/10f))
                val newDay= 15+(currentDragX/(size.width/10f)).toInt()
                onDayChanged(newDay)
            }
        }

    ){

        val top = 0f
        val bot = scaleWidth.toPx()

        drawContext.canvas.nativeCanvas.apply {

            drawRect(
                Rect(
                    -2000,top.toInt(),size.width.toInt()+2000,bot.toInt()
                ),
                Paint().apply {
                    color = Color.WHITE
                    setShadowLayer(
                        30f,
                        0f,
                        0f,
                        Color.argb(50,0,0,0)
                    )
                }
            )
        }

        for(i in 1..31){
            val currentX = i*(size.width/10)-currentDragX-size.width

            val lineStart = Offset(
                x = currentX,
                y = 0f
            )
            val lineEnd = Offset(
                x = currentX,
                y = style.lineLength
            )

            drawLine(
                color = style.lineColor,
                strokeWidth = 1.dp.toPx(),
                start = lineStart,
                end = lineEnd
            )
            drawContext.canvas.nativeCanvas.apply {
                val x = currentX
                val y = style.lineLength + 5.dp.toPx() + style.textSize.toPx()
                drawText(
                    abs(i).toString(),
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
}

@Composable
fun DatePickerMonth(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(60.dp),
    style: StyleDatePicker = StyleDatePicker(lineColor = blau,scaleIndicatorColor = blau),
    onMonthChanged: (String) -> Unit
) {
    val scaleWidth = style.scaleWidth
    val months = listOf("Jan","Feb","Mrz","Apr","Mai","Jun","Jul","Aug","Sep","Okt","Nov","Dez")


    var dragStartedX by remember{
        mutableStateOf(0f)
    }

    var currentDragX by remember {
        mutableStateOf(0f)
    }

    var oldX by remember{
        mutableStateOf(currentDragX)
    }

    Canvas(
        modifier = modifier
            .pointerInput(true){
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStartedX = offset.x
                    },
                    onDragEnd = {
                        val rest = currentDragX % (size.width/6f)
                        val roundUp = round(abs(rest/(size.width/6f))).toInt() == 1
                        var newX = 0f
                        if(roundUp){
                            if(rest<0){
                                newX = currentDragX + abs(rest) - size.width/6f
                            }else{
                                newX = currentDragX - rest + size.width/6f
                            }
                        }else{
                            if(rest<0){
                                newX = currentDragX + abs(rest)
                            }else{
                                newX = currentDragX - rest
                            }
                        }
                        currentDragX = newX.coerceIn(
                            minimumValue = -5/6f*size.width,
                            maximumValue = size.width*1f
                        )
                        oldX = currentDragX
                        val newMonth = months[5+(currentDragX/(size.width/6f)).toInt()]
                        onMonthChanged(newMonth)
                    }
                )
                { change, _ ->

                    val changeX = change.position.x
                    var newX = oldX + (dragStartedX-changeX)
                    currentDragX = newX.coerceIn(
                        minimumValue = -1.5f*size.width,
                        maximumValue = size.width*1.5f
                    )
                    val newMonth = months[5+(currentDragX/(size.width/6f)).toInt()]
                    onMonthChanged(newMonth)
                }
            }

    ){


        val top = 0f
        val bot = scaleWidth.toPx()

        drawContext.canvas.nativeCanvas.apply {

            drawRect(
                Rect(
                    -2000,top.toInt(),size.width.toInt()+2000,bot.toInt()
                ),
                Paint().apply {
                    color = Color.WHITE
                    setShadowLayer(
                        30f,
                        0f,
                        0f,
                        Color.argb(50,0,0,0)
                    )
                }
            )
        }

        for(i in 1..12){
            val currentX = i*(size.width/6)-currentDragX-size.width/2f

            val lineStart = Offset(
                x = currentX,
                y = 0f
            )
            val lineEnd = Offset(
                x = currentX,
                y = style.lineLength
            )

            drawLine(
                color = style.lineColor,
                strokeWidth = 1.dp.toPx(),
                start = lineStart,
                end = lineEnd
            )
            drawContext.canvas.nativeCanvas.apply {
                val x = currentX
                val y = style.lineLength + 5.dp.toPx() + style.textSize.toPx()
                drawText(
                    months[i-1],
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
}

@Composable
fun DatePickerYear(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(60.dp),
    style: StyleDatePicker = StyleDatePicker(lineColor = blau,scaleIndicatorColor = blau),
    onYearChanged: (Int) -> Unit
) {
    val scaleWidth = style.scaleWidth

    var dragStartedX by remember{
        mutableStateOf(0f)
    }

    var currentDragX by remember {
        mutableStateOf(0f)
    }

    var oldX by remember{
        mutableStateOf(currentDragX)
    }

    Canvas(modifier = modifier
        .pointerInput(true){
            detectDragGestures(
                onDragStart = { offset ->
                    dragStartedX = offset.x
                },
                onDragEnd = {
                    val rest = currentDragX % (size.width/6f)
                    val roundUp = round(abs(rest/(size.width/6f))).toInt() == 1
                    var newX = 0f
                    if(roundUp){
                        if(rest<0){
                            newX = currentDragX + abs(rest) - size.width/6f
                        }else{
                            newX = currentDragX - rest + size.width/6f
                        }
                    }else{
                        if(rest<0){
                            newX = currentDragX + abs(rest)
                        }else{
                            newX = currentDragX - rest
                        }
                    }
                    currentDragX = newX.coerceIn(
                        minimumValue = -size.width*10f-size.width/3f,
                        maximumValue = size.width*3f
                    )
                    oldX = currentDragX
                    val newYear = round(1992f+(currentDragX/(size.width/6f))).toInt()
                    onYearChanged(newYear)
                }
            )
            { change, _ ->

                val changeX = change.position.x
                var newX = oldX + (dragStartedX-changeX)
                currentDragX = newX.coerceIn(
                    minimumValue = -size.width*10f-size.width/3f,
                    maximumValue = size.width*3f
                )
                val newYear = round(1992f+(currentDragX/(size.width/6f))).toInt()
                onYearChanged(newYear)
            }
        }

    ){

        val top = 0f
        val bot = scaleWidth.toPx()

        drawContext.canvas.nativeCanvas.apply {

            drawRect(
                Rect(
                    -10000,top.toInt(),size.width.toInt()+10000,bot.toInt()
                ),
                Paint().apply {
                    color = Color.WHITE
                    setShadowLayer(
                        30f,
                        0f,
                        0f,
                        Color.argb(50,0,0,0)
                    )
                }
            )
        }
        var counter = 1
        for(i in 1930..2010){
            val currentX = counter*(size.width/6)-currentDragX-size.width*10

            val lineStart = Offset(
                x = currentX,
                y = 0f
            )
            val lineEnd = Offset(
                x = currentX,
                y = style.lineLength
            )

            drawLine(
                color = style.lineColor,
                strokeWidth = 1.dp.toPx(),
                start = lineStart,
                end = lineEnd
            )
            drawContext.canvas.nativeCanvas.apply {
                val x = currentX
                val y = style.lineLength + 5.dp.toPx() + style.textSize.toPx()
                drawText(
                    abs(i).toString(),
                    x,
                    y,
                    Paint().apply {
                        textSize = style.textSize.toPx()
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
            counter++

        }


    }
}

@Composable
fun BirtdayText(
    day: String,
    month: String,
    year: String
) {
    Text(text = "$day. $month $year", fontWeight = FontWeight.SemiBold,fontSize = 25.sp)
}

@Composable
fun RectAngleBirthday() {
    val color = blau
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ){
        drawRect(
            size = Size(50.dp.toPx(),270.dp.toPx()),
            topLeft = Offset(center.x-25.dp.toPx(),160.dp.toPx()),
            style = Stroke(width = 12.0f, join = StrokeJoin.Round),
            brush = Brush.linearGradient(listOf(color,color))
        )
    }
}