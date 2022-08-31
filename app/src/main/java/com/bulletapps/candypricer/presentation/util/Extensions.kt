package com.bulletapps.candypricer.presentation.util

import android.content.Context
import android.content.Intent
import android.icu.text.NumberFormat
import android.net.Uri
import android.telephony.PhoneNumberUtils
import com.bulletapps.candypricer.data.response.UnitResponse
import java.math.RoundingMode
import java.net.URLEncoder
import java.text.DecimalFormat
import java.util.*


const val ZERO = 0
const val NEGATIVE = -1
const val WHATSAPP_NUMBER = "+5586981133033"
const val ZERO_DOUBLE = 0.0
const val ZERO_FLOAT = 0f
const val ONE_HUNDRED = 100.0
const val COUNTRY = "BR"
const val LANGUAGE = "pt"

fun Int?.orZero() = this ?: ZERO
fun Int?.orNegative() = this ?: NEGATIVE
fun Double?.orZero() = this ?: ZERO_DOUBLE
fun Float?.orZero() = this ?: ZERO_FLOAT

fun Int.isNegative() = this < 0

fun Boolean?.orFalse() = this ?: false

fun Context.openWhatsapp(phone: String = WHATSAPP_NUMBER, message: String = "Oi!") {
    val url = "https://api.whatsapp.com/send?phone=$phone"+"&text=" + URLEncoder.encode(message, "UTF-8")
    Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
        startActivity(this)
    }
}

fun Double?.toCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(this ?: ZERO_DOUBLE)
}

fun Double?.toPercent() = this.orZero()/ONE_HUNDRED

fun Double?.toPercentString() = "${(this.orZero()*ONE_HUNDRED).round()}%"

fun Double.round(): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN
    return df.format(this)
}

fun String?.formatDouble(): Double {
    val value = if (this.isNullOrEmpty()) "0.0" else this
    return value.replace(",", ".").replace("%", "").toDouble()
}

fun String?.filterNumbers(): String {
    val string = this.orEmpty()
    return string.replace("[^0-9]".toRegex(), "")
}

fun Context.navigateUrl(url: String) {
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    startActivity(i)
}

fun String?.formatPhone(): String {
    return if (this.isNullOrEmpty()) "" else PhoneNumberUtils.formatNumber(this, Locale.getDefault().country)
}

fun String?.formatUnit() = when (this) {
    "UND" -> "Und."
    "KG" -> "Kg"
    "G" -> "g"
    "L" -> "l"
    "ML" -> "ml"
    else -> this.orEmpty()
}

fun List<UnitResponse>.format(): List<UnitResponse> {
    if (isEmpty()) return emptyList()
    return this.map { UnitResponse(it.id, it.name.formatUnit()) }
}

fun UnitResponse?.format() = UnitResponse(this?.id ?: -1, this?.name.formatUnit())