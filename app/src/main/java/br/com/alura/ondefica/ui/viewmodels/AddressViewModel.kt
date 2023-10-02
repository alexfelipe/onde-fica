package br.com.alura.ondefica.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class AddressViewModel : ViewModel() {

    private val httpClient = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    private val _uiState = MutableStateFlow(AddressFormUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun findAddress(cep: String) {
        _uiState.update {
            it.copy(
                isLoading = true,
                isError = false
            )
        }
        _uiState.update {
            try {
                httpClient.get("https://viacep.com.br/ws/$cep/json/")
                    .body<AddressResponse>()
                    .toAddressFormUiState()
            } catch (t: Throwable) {
                Log.e("AddressViewModel", "findAddress: ", t)
                _uiState.value.copy(
                    isError = true,
                    isLoading = false
                )
            }
        }
    }

}

@Serializable
class AddressResponse(
    private val logradouro: String,
    private val bairro: String,
    @SerialName("localidade")
    private val cidade: String,
    @SerialName("uf")
    private val estado: String
) {
    fun toAddressFormUiState() = AddressFormUiState(
        logradouro = logradouro,
        bairro = bairro,
        cidade = cidade,
        estado = estado,
        isLoading = false,
        isError = false
    )
}

data class AddressFormUiState(
    val logradouro: String = "",
    val bairro: String = "",
    val cidade: String = "",
    val estado: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false
)