package kerasinoapps.kapps.infona.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import dagger.hilt.android.AndroidEntryPoint
import kerasinoapps.kapps.infona.auth_module.presentation.ui.LoginScreen
import kerasinoapps.kapps.infona.auth_module.presentation.ui.RegisterScreen
import kerasinoapps.kapps.infona.auth_module.presentation.ui.UniversityScreen
import kerasinoapps.kapps.infona.auth_module.presentation.ui.ValidationScreen
import kerasinoapps.kapps.infona.auth_module.presentation.ui.basicdata.AverageGradeScreen
import kerasinoapps.kapps.infona.auth_module.presentation.ui.basicdata.BirthdayScreen
import kerasinoapps.kapps.infona.auth_module.presentation.ui.basicdata.GenderScreen
import kerasinoapps.kapps.infona.auth_module.presentation.ui.basicdata.NameScreen
import kerasinoapps.kapps.infona.auth_module.presentation.viewmodel.BasicDataViewModel
import kerasinoapps.kapps.infona.common.Constants.REGISTER_DONE
import kerasinoapps.kapps.infona.data.local.preferences.UserPreferences
import kerasinoapps.kapps.infona.main_module.presentation.ui.*
import kerasinoapps.kapps.infona.ui.theme.InfonaTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @Inject
    protected lateinit var userPreferences: UserPreferences

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            InfonaTheme {

                val navController = rememberNavController()
                var startDestination = ""
                if(userPreferences.getRegisterStatus() == REGISTER_DONE){
                    startDestination = NestedScreenRoute.MainRoute.route
                }else{
                    startDestination = NestedScreenRoute.AuthRoute.route
                }

                NavHost(navController = navController, startDestination = startDestination){


                    navigation(startDestination = ScreenRoute.RegisterScreen.route, route = NestedScreenRoute.AuthRoute.route){
                        composable(ScreenRoute.LoginScreen.route){
                            LoginScreen(navController = navController)
                        }
                        composable(ScreenRoute.RegisterScreen.route){
                            RegisterScreen(navController = navController)
                        }
                        composable(ScreenRoute.ValidationScreen.route +"/{userEmail}"){ backStackEntry ->
                            ValidationScreen(navController = navController, userEmail = backStackEntry.arguments?.getString("userEmail"))
                        }
                        composable(ScreenRoute.NameScreen.route){
                            val viewModel = hiltViewModel<BasicDataViewModel>()
                            NameScreen(viewModel, navController)
                        }
                        composable(ScreenRoute.GenderScreen.route){
                            val parentEntry = remember{
                                navController.getBackStackEntry(ScreenRoute.NameScreen.route)
                            }
                            val viewModel = hiltViewModel<BasicDataViewModel>(parentEntry)
                            GenderScreen(viewModel, navController)
                        }
                        composable(ScreenRoute.BirthdayScreen.route){
                            val parentEntry = remember{
                                navController.getBackStackEntry(ScreenRoute.NameScreen.route)
                            }
                            val viewModel = hiltViewModel<BasicDataViewModel>(parentEntry)
                            BirthdayScreen(viewModel, navController)
                        }

                        composable(ScreenRoute.UniversityScreen.route){
                            val parentEntry = remember {
                                navController.getBackStackEntry(ScreenRoute.NameScreen.route)
                            }
                            val viewModel = hiltViewModel<BasicDataViewModel>(parentEntry)
                            UniversityScreen(viewModel = viewModel, navController = navController)
                        }

                        composable(ScreenRoute.AverageGradeScreen.route){
                            val parentEntry = remember{
                                navController.getBackStackEntry(ScreenRoute.NameScreen.route)
                            }
                            val viewModel = hiltViewModel<BasicDataViewModel>(parentEntry)
                            AverageGradeScreen(viewModel, navController)
                        }

                    }

                    navigation(startDestination = ScreenRoute.MainMenuScreen.route, route = NestedScreenRoute.MainRoute.route){
                        composable(ScreenRoute.MainMenuScreen.route){
                            MainMenuScreen(navController = navController)
                        }
                        composable(ScreenRoute.SettingsScreen.route){
                            SettingsScreen(navController = navController)
                        }
                        composable(ScreenRoute.TopicScreen.route +"/{mainColorCode}/{subjectId}/{subjectName}"){ backStackEntry ->
                            TopicScreen(
                                navController = navController,
                                mainColorCode = backStackEntry.arguments?.getString("mainColorCode"),
                                subjectId = backStackEntry.arguments?.getString("subjectId"),
                                subjectName = backStackEntry.arguments?.getString("subjectName")
                            )
                        }
                        composable(ScreenRoute.ExerciseScreen.route+"/{mainColorCode}/{topicId}/{topicName}"){ backStackEntry ->
                              ExerciseScreen(
                                  navController = navController,
                                  mainColorCode = backStackEntry.arguments?.getString("mainColorCode"),
                                  topictId = backStackEntry.arguments?.getString("topicId"),
                                  topicName = backStackEntry.arguments?.getString("topicName")
                              )
                        }
                        composable(ScreenRoute.RanglisteScreen.route){
                            RanglisteScreen(navController = navController)
                        }
                        composable(ScreenRoute.ProfileScreen.route+"/{userId}"){ backStackEntry ->
                            ProfileScreen(
                                navController = navController,
                                userId = backStackEntry.arguments?.getString("userId")
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InfonaTheme {
        Greeting("Android")
    }
}