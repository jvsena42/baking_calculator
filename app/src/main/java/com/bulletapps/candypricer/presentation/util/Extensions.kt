package com.bulletapps.candypricer.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri

private const val ZERO = 0
private const val ZERO_DOUBLE = 0.0
private const val ZERO_FLOAT = 0f

fun Int?.orZero() = this ?: ZERO
fun Double?.orZero() = this ?: ZERO_DOUBLE
fun Float?.orZero() = this ?: ZERO_FLOAT

fun Context.openWhatsapp(phone: String, message: String = "Oi!") {
    try {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            putExtra("jid", "${phone}@s.whatsapp.net")
            type = "text/plain"
            setPackage("com.whatsapp")
        }
        startActivity(sendIntent)
    }catch (e: Exception){
        e.printStackTrace()
        val appPackageName = "com.whatsapp"
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (e :android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }
    }
}
