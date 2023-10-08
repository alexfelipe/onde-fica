package br.com.alura.ondefica.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.alura.ondefica.ui.repositories.AddressRepository
import br.com.alura.ondefica.ui.uistates.AddressFormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddressViewModel(
    private val repository: AddressRepository
) : ViewModel() {

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
                repository.findAddress(cep)
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

