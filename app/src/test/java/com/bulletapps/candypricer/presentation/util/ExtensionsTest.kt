package com.bulletapps.candypricer.presentation.util

import junit.framework.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `formatDouble() should display correct result`() {
        val input = "598,9"
        val converted = input.formatDouble()
        val expectedResult = 598.9

        assertEquals(expectedResult, converted)
    }

    @Test
    fun `given nul input formatDouble() should display correct result`() {
        val input: String? = null
        val converted = input.formatDouble()
        val expectedResult = 0.0

        assertEquals(expectedResult, converted)
    }

    @Test
    fun `given input filterNumbers() should display correct result`() {
        val input = "+55 (86) 9 9800-6407"
        val converted = input.filterNumbers()
        val expectedResult = "5586998006407"

        assertEquals(expectedResult, converted)
    }
    @Test
    fun `given null input filterNumbers() should display correct result`() {
        val input: String? = null
        val converted = input.filterNumbers()
        val expectedResult = ""

        assertEquals(expectedResult, converted)
    }
}