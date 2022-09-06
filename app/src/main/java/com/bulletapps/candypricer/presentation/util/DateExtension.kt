package com.bulletapps.candypricer.presentation.util

import com.bulletapps.candypricer.presentation.util.DateConstant.BACKEND_FORMAT
import com.bulletapps.candypricer.presentation.util.DateConstant.DAY_MONTH_YEAR_FORMAT
import com.bulletapps.candypricer.presentation.util.DateConstant.HOUR_FORMAT
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateConstant {
    const val BACKEND_FORMAT = "yyyy-MM-dd"
    const val DAY_MONTH_YEAR_FORMAT = "dd/MM/yyyy"
    const val HOUR_FORMAT = "HH:mm"
}

fun String.toDate(format: String = BACKEND_FORMAT) = SimpleDateFormat(format, Locale.getDefault()).parse(this) ?: throw NotDateFoundException()

fun Date.formatToHour() = format(HOUR_FORMAT)
fun Date.formatToDayMonthYear() = format(DAY_MONTH_YEAR_FORMAT)

private fun Date.format(format: String) = SimpleDateFormat(format, LOCALE_BR).format(this).orEmpty()

class NotDateFoundException : RuntimeException()