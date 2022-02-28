package kerasinoapps.kapps.infona.main_module.presentation.composables.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kerasinoapps.kapps.infona.auth_module.presentation.composables.CustomTextField
import kerasinoapps.kapps.infona.auth_module.presentation.composables.CustomTextFieldWithoutIcon
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Exercise
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.ExerciseInfo
import kerasinoapps.kapps.infona.ui.theme.blau
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau
import kerasinoapps.kapps.infona.ui.theme.weiss


@Composable
fun ExerciseInfo(
    exerciseTyp:String,
    modifier: Modifier = Modifier,
    selectionPossibilities:List<String>? = null,
    multipleChoiceSelections:List<Boolean>,
    singleChoiceSelections:List<Boolean>,
    color: Color,
    onMultipleChoiceSelectionChange:(index:Int,value:Boolean)->Unit,
    onSingleChoiceSelectionChange:(index:Int,value:Boolean)->Unit,
    singleEntryValue:String,
    onSingleEntryValueChanged:(String)->Unit,
    exercise:Exercise,
    arrayEntryValue:List<String>,
    onArrayEntryValueChanged:(Int,String)->Unit
) {

    if(exerciseTyp=="MultipleChoice"){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .shadow(5.dp, RoundedCornerShape(12.dp))
                .background(
                    weiss,
                    RoundedCornerShape(12.dp)
                )
                .padding(start = 7.dp, top = 10.dp, end = 7.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ){
            ExerciseInfoRow(
                text = selectionPossibilities?.get(0) ?:"",
                selected = multipleChoiceSelections[0],
                onClick = {
                          onMultipleChoiceSelectionChange(0,!multipleChoiceSelections[0])
                },
                buttonColor = color,
                modifier = Modifier.fillMaxWidth()
            )
            if (selectionPossibilities != null) {
                for(answer in selectionPossibilities){
                    val index = selectionPossibilities.indexOf(answer)
                    if(index>0){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp)
                                .height(1.dp)
                                .background(color = color)
                        ) {

                        }
                        ExerciseInfoRow(
                            text = answer,
                            selected = multipleChoiceSelections[index],
                            onClick = {
                                onMultipleChoiceSelectionChange(index,!multipleChoiceSelections[index])
                            },
                            buttonColor = color,
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                }
            }
        }
    }else if(exerciseTyp == "SingleChoice"){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .shadow(5.dp, RoundedCornerShape(12.dp))
                .background(
                    weiss,
                    RoundedCornerShape(12.dp)
                )
                .padding(start = 7.dp, top = 10.dp, end = 7.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ){
            ExerciseInfoRow(
                text = selectionPossibilities?.get(0) ?:"",
                selected = singleChoiceSelections[0],
                onClick = {
                    onSingleChoiceSelectionChange(0,!multipleChoiceSelections[0])
                },
                buttonColor = color,
                modifier = Modifier.fillMaxWidth()
            )
            if (selectionPossibilities != null) {
                for(answer in selectionPossibilities){
                    val index = selectionPossibilities.indexOf(answer)
                    if(index>0){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp)
                                .height(1.dp)
                                .background(color = color)
                        ) {

                        }
                        ExerciseInfoRow(
                            text = answer,
                            selected = singleChoiceSelections[index],
                            onClick = {
                                onSingleChoiceSelectionChange(index,!multipleChoiceSelections[index])
                            },
                            buttonColor = color,
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                }
            }
        }
    }else if(exerciseTyp == "SingleEntry"){
        CustomTextFieldWithoutIcon(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            value = singleEntryValue,
            backgroundColor = weiss,
            cursorColor = blau,
            textColor = dunkelgrau,
            placeholderText = "",
            textFieldSize = 14,
            keyboardType = KeyboardType.Ascii,
            visualTransformation = VisualTransformation.None,
            onValueChanged = onSingleEntryValueChanged,
            roundedCornerShape = 5,
            textAlign = TextAlign.Center
        )
    }else if(exerciseTyp == "ArrayEntry"){
        val answerCount = exercise.musterLoesung.loesungArray.size
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){
            for (i in 0..answerCount-1){
                var inputString = ""
                if(arrayEntryValue.size>i){
                    inputString = arrayEntryValue[i]
                }
                CustomTextFieldWithoutIcon(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    value = inputString,
                    backgroundColor = weiss,
                    cursorColor = blau,
                    textColor = dunkelgrau,
                    placeholderText = "",
                    textFieldSize = 14,
                    keyboardType = KeyboardType.Ascii,
                    visualTransformation = VisualTransformation.None,
                    onValueChanged = {
                         onArrayEntryValueChanged(i,it)
                    },
                    roundedCornerShape = 5,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}

@Composable
fun ExerciseInfoRow(
    text:String,
    modifier: Modifier = Modifier,
    selected:Boolean,
    onClick:()->Unit,
    buttonColor: Color
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = text,
            fontSize = 13.sp
        )
        RadioButton(
            selected = selected,
            onClick = {
                onClick()
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = buttonColor,
                unselectedColor = buttonColor
            ),
            modifier = Modifier
                .scale(0.75f,0.75f)
        )
    }

}