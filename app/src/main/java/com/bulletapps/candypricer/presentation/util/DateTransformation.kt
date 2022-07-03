package com.bulletapps.candypricer.presentation.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DateTransformation: VisualTransformation {
    //DD/MM/YYYY
    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        text.text.forEachIndexed { index, char ->
            out += when(index) {
                2 -> "/$char"
                4 -> "/$char"
                else -> char
            }
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when(offset) {
                    2 -> offset
                    4 -> offset + 1
                    else -> offset + 2
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset - 1
                    else -> offset - 2
                }


            }

        }

        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }
}