package kerasinoapps.kapps.infona.main_module.presentation.ui



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kerasinoapps.kapps.infona.main_module.presentation.composables.topic.TopicEntry
import kerasinoapps.kapps.infona.main_module.presentation.viewmodel.topic.TopicViewModel
import kerasinoapps.kapps.infona.navigation.ScreenRoute
import kerasinoapps.kapps.infona.ui.theme.weiss

@Composable
fun TopicScreen(
    navController: NavController,
    viewModel:TopicViewModel = hiltViewModel(),
    mainColorCode: String?,
    subjectId:String?,
    subjectName:String?
) {

    val state = viewModel.topicState.value
    val topics = viewModel.topics.value
    val mainColor = Color (mainColorCode?.toInt() ?: 0)

    LaunchedEffect(key1 = true){
        viewModel.getValues(subjectId = subjectId ?: "0")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(mainColorCode?.toInt() ?: 0))
            .padding(15.dp)

    ) {
        Text(
            text = subjectName ?: "0",
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            color = weiss
        )
        Spacer(modifier = Modifier.padding(top=20.dp))
        if(state.showData){

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ){
                items(topics.size){ index ->
                    TopicEntry(
                        name = topics.get(index).name,
                        doneExercises = state.exerciseInfoList[index].doneExercises,
                        exerciseCount = state.exerciseInfoList[index].exerciseCount,
                        textColor = mainColor,
                        onClick = {
                            navController.navigate(ScreenRoute.ExerciseScreen.route+"/${mainColorCode}/${topics.get(index).id}/${topics.get(index).name}")
                        }
                    )
                }
            }
        }else if(state.error!=null){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
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
                            .fillMaxWidth(0.9f),
                        color = weiss
                    )
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "refresh_subjects",
                        tint = weiss,
                        modifier = Modifier
                            .clickable {
                                viewModel.getValues(subjectId ?: "0")
                            }
                    )
                }
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
        }
    }

}