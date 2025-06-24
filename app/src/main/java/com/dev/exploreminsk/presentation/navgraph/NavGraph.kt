package com.dev.exploreminsk.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dev.exploreminsk.presentation.explore_minsk_navigator.ExploreMinskNavigator
import com.dev.exploreminsk.presentation.login.LoginScreen
import com.dev.exploreminsk.presentation.login.LoginViewModel
import com.dev.exploreminsk.presentation.navgraph.Route.AppNavigation
import com.dev.exploreminsk.presentation.onboarding.OnBoardingScreen
import com.dev.exploreminsk.presentation.onboarding.OnBoardingViewModel
import com.dev.exploreminsk.presentation.signup.SignUpScreen
import com.dev.exploreminsk.presentation.signup.SignUpViewModel

@Composable
fun NavGraph(startDestination: String) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route,
            ) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    onComplete = {
                        navController.navigate(Route.AuthNavigation.route) {
                            popUpTo(Route.AppStartNavigation.route) { inclusive = true }
                        }
                    },
                    onEvent = viewModel::onEvent
                )
            }
        }

        navigation(
            route = Route.AuthNavigation.route,
            startDestination = Route.LoginScreen.route
        ) {
            composable(route = Route.LoginScreen.route) {
                val viewModel: LoginViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState().value
                LoginScreen(
                    state,
                    onEvent = viewModel::onEvent,
                    navigateToSignUp = {
                        navController.navigate(Route.SignUpScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onLoginSuccess = {
                        navController.navigate(AppNavigation.route) {
                            popUpTo(Route.AuthNavigation.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(route = Route.SignUpScreen.route) {
                val viewModel: SignUpViewModel = hiltViewModel()
                val state = viewModel.state.collectAsState().value
                SignUpScreen(
                    navigateToLogin = {
                        navController.navigate(Route.LoginScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onEvent = viewModel::onEvent,
                    state = state
                )
            }
        }

        navigation(
            route = Route.AppNavigation.route,
            startDestination = Route.ExploreMinskNavigator.route
        ) {
            composable(route = Route.ExploreMinskNavigator.route) {
                ExploreMinskNavigator()
            }
        }

    }

}