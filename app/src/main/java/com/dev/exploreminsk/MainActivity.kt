package com.dev.exploreminsk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dev.exploreminsk.presentation.navgraph.NavGraph
import com.dev.exploreminsk.presentation.settings.components.AppLanguage
import com.dev.exploreminsk.presentation.settings.components.AppTheme
import com.dev.exploreminsk.presentation.ui.theme.ExploreMinskTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashCondition
            }
        }
        enableEdgeToEdge()
        setContent {
            var theme = viewModel.appTheme.collectAsState()
            var language = viewModel.appLanguage.collectAsState()
            val lastLanguage = rememberSaveable { mutableStateOf<AppLanguage?>(null) }

            val isDarkTheme = when (theme.value) {
                AppTheme.LIGHT -> false
                AppTheme.DARK -> true
                AppTheme.SYSTEM -> isSystemInDarkTheme()
            }

            LaunchedEffect(language.value) {
                if (lastLanguage.value != language.value) {
                    lastLanguage.value = language.value
                    updateLocale(language.value)
                    recreate()
                }
            }

            ExploreMinskTheme(darkTheme = isDarkTheme) {
                NavGraph(
                    startDestination = viewModel.startDestination,
                )
            }


        }
    }

    private fun updateLocale(language: AppLanguage) {
        val locale = when (language) {
            AppLanguage.ru -> Locale("ru")
            AppLanguage.en -> Locale("en")
        }
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
