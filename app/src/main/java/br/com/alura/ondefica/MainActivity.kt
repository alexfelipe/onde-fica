package br.com.alura.ondefica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import br.com.alura.ondefica.ui.screens.AddressForm
import br.com.alura.ondefica.ui.theme.OndeFicaTheme
import br.com.alura.ondefica.ui.viewmodels.AddressViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OndeFicaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val scope = rememberCoroutineScope()
                    val viewModel = koinViewModel<AddressViewModel>()
                    val uiState by viewModel.uiState.collectAsState()
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