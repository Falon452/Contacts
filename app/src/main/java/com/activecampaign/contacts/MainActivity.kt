package com.activecampaign.contacts

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.activecampaign.contacs.presentation.ui.ContactsScreen
import com.activecampaign.contacts.ui.LogoInBackground
import com.activecampaign.contacts.util.expandAndFadeAnimationRun
import com.activecampaign.contacts.viewmodel.MainViewModel
import com.falon.theme.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSplashScreen()
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val mainViewModel = hiltViewModel<MainViewModel>()
                val mainState = mainViewModel.state.collectAsStateWithLifecycle()
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) },
                            actions = {
                                Switch(
                                    checked = mainState.value.isDarkMode,
                                    onCheckedChange = mainViewModel::onToggleTheme
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        )
                    }
                ) { innerPading ->
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = Routes.CONTACTS) {
                        composable(Routes.CONTACTS) {
                            LogoInBackground()
                            ContactsScreen(
                                modifier = Modifier.padding(innerPading)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setupSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.iconView?.expandAndFadeAnimationRun {
                    splashScreenView.remove()
                }
            }
        }
    }
}
