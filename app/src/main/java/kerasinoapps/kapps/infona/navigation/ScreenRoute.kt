package kerasinoapps.kapps.infona.navigation

sealed class ScreenRoute (val route:String){
    object RegisterScreen:ScreenRoute("register_screen")
    object LoginScreen:ScreenRoute("login_screen")
    object ValidationScreen:ScreenRoute("validation_screen")
    object NameScreen:ScreenRoute("name_screen")
    object GenderScreen:ScreenRoute("gender_screen")
    object BirthdayScreen:ScreenRoute("birthday_screen")
    object UniversityScreen:ScreenRoute("university_screen")
    object AverageGradeScreen:ScreenRoute("average_grade_screen")

    object MainMenuScreen:ScreenRoute("main_menu_screen")
    object SettingsScreen:ScreenRoute("settings_screen")
    object TopicScreen:ScreenRoute("topic_screen")
    object ExerciseScreen:ScreenRoute("exercise_screen")
    object RanglisteScreen:ScreenRoute("rangliste_screen")
    object ProfileScreen:ScreenRoute("profile_screen")
}

sealed class NestedScreenRoute(val route:String){
    object AuthRoute: NestedScreenRoute("auth_route")
    object MainRoute: NestedScreenRoute("main_route")
}