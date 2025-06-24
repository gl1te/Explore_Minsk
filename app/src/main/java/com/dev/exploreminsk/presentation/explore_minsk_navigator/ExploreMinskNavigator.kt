package com.dev.exploreminsk.presentation.explore_minsk_navigator

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.presentation.details.LocationDetailsScreen
import com.dev.exploreminsk.presentation.edit_profile.EditProfileScreen
import com.dev.exploreminsk.presentation.edit_profile.EditProfileViewModel
import com.dev.exploreminsk.presentation.explore.ExploreScreen
import com.dev.exploreminsk.presentation.explore.ExploreScreenViewModel
import com.dev.exploreminsk.presentation.explore_minsk_navigator.components.BottomNavigationItem
import com.dev.exploreminsk.presentation.explore_minsk_navigator.components.ExploreMinskBottomBar
import com.dev.exploreminsk.presentation.favorite.FavoritesScreen
import com.dev.exploreminsk.presentation.favorite.FavoritesViewModel
import com.dev.exploreminsk.presentation.locations.LocationsScreen
import com.dev.exploreminsk.presentation.locations.LocationsViewModel
import com.dev.exploreminsk.presentation.navgraph.Route
import com.dev.exploreminsk.presentation.profile.ProfileScreen
import com.dev.exploreminsk.presentation.profile.ProfileViewModel
import com.dev.exploreminsk.presentation.search_all.SearchAllViewModel
import com.dev.exploreminsk.presentation.search_results.SearchAll
import com.dev.exploreminsk.presentation.search_results.SearchResultsScreen
import com.dev.exploreminsk.presentation.search_results.SearchResultsViewModel
import com.dev.exploreminsk.presentation.settings.SettingsScreen
import com.dev.exploreminsk.presentation.settings.SettingsViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.gson.Gson

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExploreMinskNavigator() {

    val bottomItems = listOf(
        BottomNavigationItem(
            icon = painterResource(R.drawable.home)
        ),
        BottomNavigationItem(
            icon = painterResource(R.drawable.favorite)
        ),
        BottomNavigationItem(
            icon = painterResource(R.drawable.profile)
        ),
        BottomNavigationItem(
            icon = painterResource(R.drawable.settings)
        ),
    )

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value

    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.ExploreScreen.route -> 0
            Route.FavoritesScreen.route -> 1
            Route.ProfileScreen.route -> 2
            Route.SettingsScreen.route -> 3
            Route.EditProfileScreen.route -> 4
            else -> 0
        }
    }

    val animationDuration = 300

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            ExploreMinskBottomBar(
                bottomItems = bottomItems,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(navController, Route.ExploreScreen.route)
                        1 -> navigateToTab(navController, Route.FavoritesScreen.route)
                        2 -> navigateToTab(navController, Route.ProfileScreen.route)
                        3 -> navigateToTab(navController, Route.SettingsScreen.route)
                    }
                },
                selected = selectedItem,
            )
        }
    ) { paddingValues ->
        val bottomPadding = paddingValues.calculateBottomPadding()
        val topPadding = paddingValues.calculateTopPadding()

        AnimatedNavHost(
            navController = navController,
            startDestination = Route.ExploreScreen.route,
            modifier = Modifier.padding(top = topPadding, bottom = bottomPadding),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(animationDuration)
                ) + fadeIn(animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(animationDuration)
                ) + fadeOut(animationSpec = tween(animationDuration))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(animationDuration)
                ) + fadeIn(animationSpec = tween(animationDuration))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(animationDuration)
                ) + fadeOut(animationSpec = tween(animationDuration))
            }
        ) {
            composable(route = Route.ExploreScreen.route) {
                val viewModel: ExploreScreenViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()
                ExploreScreen(
                    navigateToDetails = {
                        navigateToPlaceDetails(navController, it)
                    },
                    onSeeAllClick = {
                        navigateToSeeAll(navController, it)
                    },
                    state = state,
                    onEvent = viewModel::onEvent,
                    navigateToSearchResults = {
                        navController.navigate(Route.SearchResultsScreen.route)
                    }
                )
            }

            composable(route = Route.FavoritesScreen.route) {
                val viewModel: FavoritesViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()
                FavoritesScreen(
                    navigateToLocation = {
                        navController.navigate(Route.ExploreScreen.route)
                    },
                    state = state,
                    onEvent = viewModel::onEvent,
                    navigateToDetails = {
                        navigateToPlaceDetails(navController, it)
                    }
                )
            }

            composable(route = Route.ProfileScreen.route) {
                val viewModel: ProfileViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()
                ProfileScreen(
                    navigateToEditProfile = {
                        navController.navigate(Route.EditProfileScreen.route)
                    },
                    navigateToLocation = {
                        navController.navigate(Route.ExploreScreen.route)
                    },
                    state = state
                )
            }

            composable(route = Route.SettingsScreen.route) {
                val viewModel: SettingsViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()
                SettingsScreen(
                    appTheme = state.value.appTheme,
                    onThemeChange = {
                        viewModel.onThemeChanged(it)
                    },
                    navigateToLocation = {
                        navController.navigate(Route.ExploreScreen.route)
                    },
                    onLanguageChanged = {
                        viewModel.onLanguageChanged(it)
                    }
                )
            }

            composable(route = Route.EditProfileScreen.route) {
                val viewModel: EditProfileViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()
                EditProfileScreen(
                    navigateToLocation = {
                        navController.navigate(Route.ExploreScreen.route)
                    },
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }

            composable(route = Route.LocationDetailsScreen.route) {
                val place =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Place>("place")
                place?.let {
                    LocationDetailsScreen(
                        navigateToLocation = {
                            navController.navigate(Route.ExploreScreen.route)
                        },
                        place = place
                    )
                }
            }

            composable(
                route = Route.LocationsScreen.route,
                arguments = listOf(navArgument("categoryJson") {
                    type = NavType.StringType
                })
            ) {
                val viewModel: LocationsViewModel = hiltViewModel()

                val state = viewModel.state.collectAsState()
                LocationsScreen(
                    navigateToLocation = {
                        navController.navigate(Route.ExploreScreen.route)
                    },
                    navigateToDetails = {
                        navigateToPlaceDetails(navController, it)
                    },
                    state = state
                )
            }

            composable(route = Route.SearchResultsScreen.route) {
                val viewModel: SearchResultsViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()
                SearchResultsScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    navigateToLocation = {
                        navController.navigate(Route.ExploreScreen.route)
                    },
                    onPlaceClick = {
                        navigateToPlaceDetails(navController, it)
                    },
                    navigateToSeeAll = {
                        navigateToSeeAllSearch(navController, it)
                    }
                )
            }

            composable(
                route = Route.SearchAll.route,
                arguments = listOf(navArgument("categoryJson") {
                    type = NavType.StringType
                })
            ) {
                val viewModel: SearchAllViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState()
                SearchAll(
                    state = state,
                    navigateUp = {
                        navController.navigateUp()
                    },
                    onPlaceClick = {
                        navigateToPlaceDetails(navController, it)
                    },
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { home ->
            popUpTo(home) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToPlaceDetails(navController: NavController, place: Place) {
    navController.currentBackStackEntry?.savedStateHandle?.set("place", place)
    navController.navigate(Route.LocationDetailsScreen.route)
}

private fun navigateToSeeAll(navController: NavController, category: Category) {
    val gson = Gson()
    val categoryJson = gson.toJson(category)
    val route = Route.LocationsScreen.createRoute(categoryJson)
    navController.navigate(route)
}

private fun navigateToSeeAllSearch(navController: NavController, category: Category) {
    val gson = Gson()
    val categoryJson = gson.toJson(category)
    val route = Route.SearchAll.createRoute(categoryJson)
    navController.navigate(route)
}