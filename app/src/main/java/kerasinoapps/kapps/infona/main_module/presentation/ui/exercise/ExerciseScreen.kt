package kerasinoapps.kapps.infona.main_module.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kerasinoapps.kapps.infona.R
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kerasinoapps.kapps.infona.main_module.presentation.composables.exercise.ExerciseInfo
import kerasinoapps.kapps.infona.main_module.presentation.composables.exercise.RoundButton
import kerasinoapps.kapps.infona.main_module.presentation.ui.exercise.CheckSolutionDialog
import kerasinoapps.kapps.infona.main_module.presentation.ui.exercise.ConfirmBuyTipDialog
import kerasinoapps.kapps.infona.main_module.presentation.viewmodel.exercise.ExerciseViewModel
import kerasinoapps.kapps.infona.ui.theme.blau
import kerasinoapps.kapps.infona.ui.theme.weiss
import java.security.AllPermission

@ExperimentalComposeUiApi
@Composable
fun ExerciseScreen(
    navController: NavController,
    viewModel:ExerciseViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    mainColorCode: String?,
    topictId:String?,
    topicName:String?
) {

    val state = viewModel.exerciseState.value
    val exerciseToShow = viewModel.exerciseToShow.value
    val exercises = viewModel.exercises.value
    val mainColor = Color (mainColorCode?.toInt() ?: 0)

    LaunchedEffect(key1 = true){
        viewModel.getExercises(topictId ?: "0")
    }

    if(viewModel.freezeScreen.value){
        Scaffold(
            scaffoldState = scaffoldState
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = mainColor)
                    .padding(10.dp)
            ) {
                val(bottomRow, header, tipIcon, aufgabeText, fetcherror, exerciseDescription, exerciseInfo) = createRefs()

                if(exerciseToShow!=null){
                    Text(
                        text = topicName ?: "",
                        color = weiss,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .constrainAs(header){
                                top.linkTo(parent.top)
                            }
                    )

                    Icon(
                        imageVector = Icons.Default.HelpOutline,
                        contentDescription = "tip_icon",
                        modifier = Modifier
                            .constrainAs(tipIcon) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                            }
                            .size(30.dp)
                            .clickable {
                                viewModel.onShowTipDialog()
                            }
                        , tint = weiss
                    )
                    Row(
                        modifier = Modifier
                            .constrainAs(aufgabeText){
                                top.linkTo(header.bottom, margin = 20.dp)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ){
                        Text(
                            text = stringResource(R.string.aufgabe, "${viewModel.getCurrentExerciseNumber()}","${exercises.size}"),
                            fontSize = 14.sp,
                            color = weiss
                        )
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "exercise_done",
                            tint = weiss,
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }

                    Text(
                        text = exerciseToShow.description,
                        fontSize = 16.sp,
                        color = mainColor,
                        modifier = Modifier
                            .fillMaxWidth()

                            .shadow(5.dp, RoundedCornerShape(12.dp))
                            .background(
                                weiss,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(start = 25.dp, top = 15.dp, end = 25.dp, bottom = 15.dp)
                            .constrainAs(exerciseDescription) {
                                top.linkTo(aufgabeText.bottom, margin = 25.dp)
                            },
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )

                    ExerciseInfo(
                        exerciseTyp = exerciseToShow.exerciseInfo.exerciseTyp,
                        selectionPossibilities = exerciseToShow.exerciseInfo.selectionPossibilities,
                        color = mainColor,
                        modifier = Modifier
                            .constrainAs(exerciseInfo){
                                top.linkTo(exerciseDescription.bottom,margin = 20.dp)
                            },
                        multipleChoiceSelections = viewModel.multipleChoiceSelections.value,
                        onMultipleChoiceSelectionChange = { index,value->

                        },
                        onSingleChoiceSelectionChange = { index, value ->

                        },
                        singleChoiceSelections = viewModel.singleChoiceSelections.value,
                        singleEntryValue = viewModel.singleEntryInput.value,
                        onSingleEntryValueChanged = {

                        },
                        exercise = exerciseToShow,
                        arrayEntryValue = viewModel.arrayEntryInput.value,
                        onArrayEntryValueChanged = { index , value ->

                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                            .constrainAs(bottomRow) {
                                bottom.linkTo(parent.bottom)
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        RoundButton(
                            imageVector = Icons.Default.ChevronLeft,
                            iconColor = mainColor,
                            onButtonClick = {
                                viewModel.changeExerciseToShow(true)
                            },
                            size = 55
                        )
                        RoundButton(
                            imageVector = Icons.Default.ChevronRight,
                            iconColor = mainColor,
                            onButtonClick = {
                                viewModel.changeExerciseToShow(false)
                            },
                            size = 55,
                        )

                    }
                }else if(state.loading){
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(25.dp),
                            color = weiss
                        )
                    }
                }else if(state.error!=null){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .constrainAs(fetcherror) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            },
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ){
                            Text(
                                text = state.error,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                            )
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "refresh_exercises",
                                modifier = Modifier
                                    .clickable {
                                        viewModel.getExercises(topictId ?: "0")
                                    }
                            )
                        }
                    }
                }
                if(viewModel.showTipDialog.value){
                    exerciseToShow?.tipps?.let {
                        TippDialog(
                            tipps = it,
                            onDismiss = { viewModel.onDismissTipDialog() },
                            viewModel = viewModel,
                            alreadyDone = true
                        )
                    }
                }
            }
        }
    }else{
        Scaffold(
            scaffoldState = scaffoldState
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = mainColor)
                    .padding(10.dp)
            ) {
                val(bottomRow, header, tipIcon, aufgabeText, fetcherror, exerciseDescription, exerciseInfo) = createRefs()

                if(exerciseToShow!=null){
                    Text(
                        text = topicName ?: "",
                        color = weiss,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .constrainAs(header){
                                top.linkTo(parent.top)
                            }
                    )

                    Icon(
                        imageVector = Icons.Default.HelpOutline,
                        contentDescription = "tip_icon",
                        modifier = Modifier
                            .constrainAs(tipIcon) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                            }
                            .size(30.dp)
                            .clickable {
                                viewModel.onShowTipDialog()
                            }
                        , tint = weiss
                    )

                    Text(
                        text = stringResource(R.string.aufgabe, "${viewModel.getCurrentExerciseNumber()}","${exercises.size}"),
                        modifier = Modifier
                            .constrainAs(aufgabeText){
                                top.linkTo(header.bottom, margin = 20.dp)
                            },
                        fontSize = 14.sp,
                        color = weiss
                    )

                    Text(
                        text = exerciseToShow.description,
                        fontSize = 16.sp,
                        color = mainColor,
                        modifier = Modifier
                            .fillMaxWidth()

                            .shadow(5.dp, RoundedCornerShape(12.dp))
                            .background(
                                weiss,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(start = 25.dp, top = 15.dp, end = 25.dp, bottom = 15.dp)
                            .constrainAs(exerciseDescription) {
                                top.linkTo(aufgabeText.bottom, margin = 25.dp)
                            },
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )

                    ExerciseInfo(
                        exerciseTyp = exerciseToShow.exerciseInfo.exerciseTyp,
                        selectionPossibilities = exerciseToShow.exerciseInfo.selectionPossibilities,
                        color = mainColor,
                        modifier = Modifier
                            .constrainAs(exerciseInfo){
                                top.linkTo(exerciseDescription.bottom,margin = 20.dp)
                            },
                        multipleChoiceSelections = viewModel.multipleChoiceSelections.value,
                        onMultipleChoiceSelectionChange = { index,value->
                            viewModel.changeMultipleChoiceSelection(index,value)
                        },
                        onSingleChoiceSelectionChange = { index, value ->
                            viewModel.changeSingleChoiceSelection(index,value)
                        },
                        singleChoiceSelections = viewModel.singleChoiceSelections.value,
                        singleEntryValue = viewModel.singleEntryInput.value,
                        onSingleEntryValueChanged = {
                            viewModel.onSingleEntryInputChanged(it)
                        },
                        exercise = exerciseToShow,
                        arrayEntryValue = viewModel.arrayEntryInput.value,
                        onArrayEntryValueChanged = { index , value ->
                            viewModel.onArrayEntryInputChanged(index,value)
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                            .constrainAs(bottomRow) {
                                bottom.linkTo(parent.bottom)
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        RoundButton(
                            imageVector = Icons.Default.ChevronLeft,
                            iconColor = mainColor,
                            onButtonClick = {
                                viewModel.changeExerciseToShow(true)
                            },
                            size = 55
                        )
                        RoundButton(
                            iconColor = mainColor,
                            onButtonClick = {
                                viewModel.onShowCheckSolutionDialog()
                            },
                            size = 75,
                            text = "${exerciseToShow.coinReward} IC$"
                        )
                        RoundButton(
                            imageVector = Icons.Default.ChevronRight,
                            iconColor = mainColor,
                            onButtonClick = {
                                viewModel.changeExerciseToShow(false)
                            },
                            size = 55,
                        )

                    }
                }else if(state.loading){
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(25.dp),
                            color = weiss
                        )
                    }
                }else if(state.error!=null){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .constrainAs(fetcherror) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            },
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ){
                            Text(
                                text = state.error,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                            )
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "refresh_exercises",
                                modifier = Modifier
                                    .clickable {
                                        viewModel.getExercises(topictId ?: "0")
                                    }
                            )
                        }
                    }
                }
                if(viewModel.showTipDialog.value){
                    exerciseToShow?.tipps?.let {
                        TippDialog(
                            tipps = it,
                            onDismiss = { viewModel.onDismissTipDialog() },
                            viewModel = viewModel,
                            alreadyDone = false
                        )
                    }
                }
                if(viewModel.showCheckSolutionDialog.value){
                    CheckSolutionDialog(
                        onDismiss = {
                            viewModel.onDismissCheckSolutionDialog()
                        },
                        onSubmit = {
                            if(exerciseToShow!!.exerciseInfo.exerciseTyp == "MultipleChoice"){
                                viewModel.checkMultipleChoiceSolution()
                            }else if(exerciseToShow!!.exerciseInfo.exerciseTyp == "SingleChoice"){
                                viewModel.checkSingleChoiceSolution()
                            }else if(exerciseToShow!!.exerciseInfo.exerciseTyp == "SingleEntry"){
                                viewModel.checkSingleInputSolution()
                            }else if(exerciseToShow!!.exerciseInfo.exerciseTyp == "ArrayEntry"){
                                viewModel.checkArrayInputSolution()
                            }
                        }
                    )
                }
                val closeString = stringResource(id = R.string.close)
                LaunchedEffect(viewModel.showGuthabenNotEnought.value){
                    if(viewModel.showGuthabenNotEnought.value != null){
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = viewModel.showGuthabenNotEnought.value!!,
                            actionLabel = closeString,
                            duration = SnackbarDuration.Short
                        )
                        viewModel.toggleShowGuthabenNotEnoughSnackbar()
                    }
                }
                val falscheAntwort = stringResource(id = R.string.antwort_falsch)
                LaunchedEffect(viewModel.showSolutionWrongSnackbar.value){
                    if(viewModel.showSolutionWrongSnackbar.value){
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = falscheAntwort,
                            actionLabel = closeString,
                            duration = SnackbarDuration.Short
                        )
                        viewModel.toggleShowSolutionWrongSnackBar()
                    }
                }
                val richtigeAntwort = stringResource(id = R.string.antwort_richtig,"${exerciseToShow?.coinReward}")
                LaunchedEffect(viewModel.showSolutionCorrectSnackbar.value){
                    if(viewModel.showSolutionCorrectSnackbar.value){
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = richtigeAntwort,
                            actionLabel = closeString,
                            duration = SnackbarDuration.Short
                        )
                        viewModel.toggleShowSolutionCorrectSnackBar()
                        viewModel.changeExerciseToShow(false)
                    }
                }

            }
        }
    }




}
