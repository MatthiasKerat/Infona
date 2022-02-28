package kerasinoapps.kapps.infona.main_module.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kerasinoapps.kapps.infona.main_module.presentation.composables.main_menu.TopBar
import kerasinoapps.kapps.infona.main_module.presentation.viewmodel.mainmenu.MainMenuViewModel
import kerasinoapps.kapps.infona.R
import kerasinoapps.kapps.infona.main_module.presentation.composables.SubjectCard
import kerasinoapps.kapps.infona.navigation.ScreenRoute
import kerasinoapps.kapps.infona.ui.theme.blau
import kerasinoapps.kapps.infona.ui.theme.dunkelgrau

@Composable
fun MainMenuScreen(
    navController: NavController,

    viewModel: MainMenuViewModel = hiltViewModel()
) {

    val state = viewModel.mainMenuState.value
    val subjects = viewModel.subjects.value
    val colorArray = listOf(
        Color(0xFF2F4F61),
        Color(0xFF3F3671),
        Color(0xFF534531),
        Color(0xFF673535),
        Color(0xFF653752),
        Color(0xFF77753D),
        Color(0xFF57773D),
        Color(0xFF2F4F61)
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (topbar, header, list, fetchError, loading) = createRefs()
        TopBar(
            letter = viewModel.initalizationLetter.value,
            modifier = Modifier
                .constrainAs(topbar){
                    top.linkTo(parent.top)
                },
            onSettingsClick = {
                navController.navigate(ScreenRoute.SettingsScreen.route)
            },
            onRanglisteClick = {
                navController.navigate(ScreenRoute.RanglisteScreen.route)
            },
            onInitializationLetterClick = {
                navController.navigate(ScreenRoute.ProfileScreen.route+"/${viewModel.appUser?.id}")
            }
        )
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 25.sp,
            modifier = Modifier
                .constrainAs(header){
                    top.linkTo(topbar.bottom, margin = 15.dp)
                    start.linkTo(topbar.start,margin = 15.dp)
                },
            color = dunkelgrau,
            fontWeight = FontWeight.SemiBold
        )
        if(state.showData){
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 130.dp)
                    .constrainAs(list) {
                        top.linkTo(header.bottom, margin = 15.dp)
                    },
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ){
                items(subjects.size){index->
                    SubjectCard(
                        name = subjects.get(index).name,
                        color = colorArray.get(index),
                        onClick = {
                            navController.navigate(ScreenRoute.TopicScreen.route+"/${colorArray.get(index).toArgb()}/${subjects.get(index).id}/${subjects.get(index).name}")
                        }
                    )
                }
            }
        }else if(state.error!=null){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .constrainAs(fetchError) {
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
                        contentDescription = "refresh_subjects",
                        modifier = Modifier
                            .clickable {
                                viewModel.getSubjects()
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
                    color = blau
                )
            }
        }

    }



}