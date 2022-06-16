package com.bulletapps.candypricer.presentation.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.min
import kotlin.math.min


object CustomArrangement {

    fun spacedBy(dp: Dp) = object : Arrangement.Vertical {

        override val spacing = dp
        override fun Density.arrange(totalSize: Int, sizes: IntArray, outPositions: IntArray) {
            if (sizes.isEmpty()) return
            val spacePx = dp.roundToPx()
            var occupied = 0
            var lastSpace = 0

            sizes.forEachIndexed { index, size ->
                if (index == sizes.lastIndex) {
                    outPositions[index] = totalSize - size
                } else {
                    outPositions[index] = min(occupied, totalSize - size)
                }
                lastSpace = min(spacePx, totalSize - outPositions[index] - size)
                occupied = outPositions[index] + size + lastSpace
            }
            occupied -= lastSpace
        }
    }
}