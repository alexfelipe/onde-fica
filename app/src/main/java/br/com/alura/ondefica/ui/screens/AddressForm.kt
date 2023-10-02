package br.com.alura.ondefica.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.ondefica.ui.theme.OndeFicaTheme

@Composable
fun AddressForm(
    onSearchAddressClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val addressTextFieldModifier = Modifier
            .fillMaxWidth()
        var cep by remember {
            mutableStateOf("")
        }
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = cep,
                onValueChange = {
                    cep = it
                },
                Modifier.weight(1f),
                label = {
                    Text(text = "CEP")
                }
            )
            IconButton(onClick = { onSearchAddressClick(cep) }) {
                Icon(
                    Icons.Default.Search,
                    "lupa indicando ação de busca",
                    Modifier.fillMaxHeight()
                )
            }
        }
        var logradouro by remember {
            mutableStateOf("")
        }
        TextField(
            value = logradouro,
            onValueChange = {
                logradouro = it
            },
            addressTextFieldModifier,
            label = {
                Text(text = "Logradouro")
            }
        )
        var numero by remember {
            mutableStateOf("")
        }
        TextField(
            value = numero,
            onValueChange = {
                numero = it
            },
            addressTextFieldModifier,
            label = {
                Text(text = "Número")
            }
        )
        var bairro by remember {
            mutableStateOf("")
        }
        TextField(
            value = bairro,
            onValueChange = {
                bairro = it
            },
            addressTextFieldModifier,
            label = {
                Text(text = "Bairro")
            }
        )
        var cidade by remember {
            mutableStateOf("")
        }
        TextField(
            value = cidade,
            onValueChange = {
                cidade = it
            },
            addressTextFieldModifier,
            label = {
                Text(text = "Cidade")
            }
        )
        var estado by remember {
            mutableStateOf("")
        }
        TextField(
            value = estado,
            onValueChange = {
                estado = it
            },
            addressTextFieldModifier,
            label = {
                Text(text = "Estado")
            }
        )
    }
}

@Preview
@Composable
fun AddressFormPreview() {
    OndeFicaTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AddressForm(
                onSearchAddressClick = {}
            )
        }
    }
}