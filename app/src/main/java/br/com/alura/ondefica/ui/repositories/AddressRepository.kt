package br.com.alura.ondefica.ui.repositories

import br.com.alura.ondefica.ui.repositories.responses.AddressResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class AddressRepository(
    private val httpClient: HttpClient
) {

    suspend fun findAddress(cep: String): AddressResponse {
        return httpClient
            .get("https://viacep.com.br/ws/$cep/json/")
            .body()
    }
}