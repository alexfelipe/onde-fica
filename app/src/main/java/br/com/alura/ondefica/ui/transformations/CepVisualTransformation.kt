package br.com.alura.ondefica.ui.transformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object CepVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val cep = text.text.mapIndexed { index, c ->
            if (index == 4) "$c-" else c
        }.joinToString(separator = "")
        return TransformedText(AnnotatedString(cep), CepOffsetMapping)
    }

    object CepOffsetMapping : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int =
            when {
                offset < 5 -> offset
                else -> offset + 1
            }

        override fun transformedToOriginal(offset: Int): Int =
            when {
                offset < 5 -> offset
                else -> offset - 1
            }

    }


}