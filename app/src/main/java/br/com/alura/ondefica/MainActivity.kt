package br.com.alura.ondefica

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import br.com.alura.ondefica.ui.screens.AddressForm
import br.com.alura.ondefica.ui.theme.OndeFicaTheme
import br.com.alura.ondefica.ui.viewmodels.AddressViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preferences")
val darkModePreference = booleanPreferencesKey("dark_mode")

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkModeFlow = remember {
                dataStore.data
                    .map {
                        it[darkModePreference] ?: false
                    }
            }
            val isDarkMode by darkModeFlow.collectAsState(initial = false)
            OndeFicaTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val scope = rememberCoroutineScope()
                    val viewModel = koinViewModel<AddressViewModel>()
                    val uiState by viewModel.uiState.collectAsState()

                    Column {
                        TopAppBar(
                            navigationIcon = {
                                Switch(
                                    checked = isDarkMode,
                                    onCheckedChange = { newChecked ->
                                        scope.launch {
                                            dataStore.edit { preferences ->
                                                preferences[darkModePreference] = newChecked
                                            }
                                        }
                                    },
                                    Modifier.padding(8.dp),
                                    thumbContent = {
                                        val icon = remember(isDarkMode) {
                                            if (isDarkMode) {
                                                Icons.Filled.NightsStay
                                            } else {
                                                Icons.Filled.WbSunny
                                            }
                                        }
                                        Icon(icon, null)
                                    }
                                )
                            },
                            title = { /*TODO*/ })
                        AddressForm(
                            uiState,
                            onSearchAddressClick = { cep ->
                                scope.launch {
                                    viewModel.findAddress(cep)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}