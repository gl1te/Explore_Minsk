package com.dev.exploreminsk.presentation.navgraph

sealed class Route(
    val route: String,
) {

    object AppStartNavigation : Route(route = "app_start_navigation")
    object AuthNavigation : Route(route = "auth_navigation")
    object AppNavigation : Route(route = "app_navigation")

    object ExploreMinskNavigator : Route(route = "explore_minsk_navigator")

    object OnBoardingScreen : Route(route = "on_boarding_screen")
    object LoginScreen : Route(route = "login_screen")
    object SignUpScreen : Route(route = "sign_up_screen")

    object ExploreScreen : Route(route = "home_screen")
    object FavoritesScreen : Route(route = "favorites_screen")
    object ProfileScreen : Route(route = "profile_screen")
    object EditProfileScreen : Route(route = "profile_edit_screen")
    object SettingsScreen : Route(route = "settings_screen")
    object LocationsScreen: Route(route = "locations_screen/{categoryJson}") {
        fun createRoute(categoryJson: String) = "locations_screen/$categoryJson"
    }

    object SearchAll: Route(route = "search_all_screen/{categoryJson}"){
        fun createRoute(categoryJson: String) = "search_all_screen/$categoryJson"
    }

    object SearchResultsScreen : Route(route = "search_results_screen")

    object LocationDetailsScreen : Route(route = "location_details_screen")

}