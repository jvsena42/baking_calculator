package com.bulletapps.candypricer.presentation.util

import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat.getCurrencyInstance

class ExtensionsTest {

//    @Test
//    fun `toCurrency should display correct result`() {
//        mockkStatic(getCurrencyInstance())
//        val doubleValue = 156.58
//        val formattedValue = doubleValue.toCurrency()
//        val expectedValue = "R$ 156.58"
//
//        assertEquals(expectedValue, formattedValue)
//    }

    @Test
    fun `formatDouble should display correct result`() {
        val input = "598,9"
        val converted = input.formatDouble()
        val expectedResult = 598.9

        assertEquals(expectedResult, converted)
    }
}