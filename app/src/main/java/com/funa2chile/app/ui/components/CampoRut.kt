package com.funa2chile.app.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import com.funa2chile.app.domain.validation.RutValidator

/**
 * Campo de RUT que NO reescribe lo que la persona escribe mientras digita.
 * Al ViewModel se le informa el RUT limpio (dígitos y K).
 * Al salir del campo, si es válido, se muestra con formato (18.221.805-7).
 */
@Composable
fun CampoRut(
    valor: String,
    onValorChange: (String) -> Unit,
    error: String?,
    modifier: Modifier = Modifier
) {
    var texto by remember { mutableStateOf(RutValidator.formatear(valor)) }

    OutlinedTextField(
        value = texto,
        onValueChange = { nuevo ->
            val filtrado = nuevo.filter {
                it.isDigit() || it == 'k' || it == 'K' || it == '.' || it == '-'
            }.take(12)
            texto = filtrado
            onValorChange(RutValidator.limpiar(filtrado).take(9))
        },
        label = { Text("RUT (ej: 12.345.678-5)") },
        leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
        trailingIcon = {
            if (error != null) {
                Icon(Icons.Default.Error, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
            } else if (texto.isNotEmpty()) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Válido", tint = MaterialTheme.colorScheme.primary)
            }
        },
        isError = error != null,
        supportingText = { if (error != null) Text(error) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, autoCorrectEnabled = false),
        modifier = modifier.onFocusChanged { foco ->
            if (!foco.isFocused && texto.isNotEmpty() && RutValidator.validate(texto) == null) {
                texto = RutValidator.formatear(RutValidator.limpiar(texto))
            }
        }
    )
}