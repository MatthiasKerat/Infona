package kerasinoapps.kapps.infona.main_module.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kerasinoapps.kapps.infona.main_module.presentation.composables.rangliste.RanglisteCard
import kerasinoapps.kapps.infona.main_module.presentation.composables.settings.BackTopBar
import kerasinoapps.kapps.infona.main_module.presentation.viewmodel.rangliste.RanglisteViewModel
import kerasinoapps.kapps.infona.navigation.ScreenRoute
import kerasinoapps.kapps.infona.ui.theme.*

@Composable
fun RanglisteScreen(
    navController: NavController,
    viewModel:RanglisteViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val users = viewModel.users.value

    LaunchedEffect(key1 = true){
        viewModel.getUsers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        BackTopBar(
            onBackClick = {
                navController.popBackStack()
            }
        )

        if(state.loading){
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
        }else if(state.error!=null){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
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
                                viewModel.getUsers()
                            }
                    )
                }
            }
        }else if(state.showData){

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 130.dp, top = 13.dp),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ){
                items(users.size){index->
                    var color = dunkelgrau
                    when(index){
                        0 -> {color = gold}
                        1 -> {color = silver}
                        2 -> {color = bronze}
                    }
                    RanglisteCard(
                        user = users[index],
                        borderColor = color,
                        onClick = {
                            navController.navigate(ScreenRoute.ProfileScreen.route+"/${users[index].id}")
                        },
                        picture = users[index].userImage
                    )
                }
            }

        }
    }




}