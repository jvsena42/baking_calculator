package com.bulletapps.candypricer.presentation.ui.widgets

import android.widget.DatePicker

class DatePicker {
    companion object {
        const val ZERO = 0
        const val UTC_TIMEZONE = "UTC"
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val DAY_MONTH_YEAR_FORMAT = "dd/MM/yyy"
    }

    data class DatePickerBuilder(
        var title: String = "",
        var presentationDateFormatResult: String = DAY_MONTH_YEAR_FORMAT,
        var dateFormatResult: String = DATE_FORMAT,
        var selectedDate: Long? = null,
        var validDateRange: Pair<Long, Long>? = null,
        var validOnlyDateAfter: Long? = null,
        var validDateUntil: Long? = null,
//        var onDateSelect: (DatePicker.Result) -> Unit = {},
        var onCancel: () -> Unit = {},
        var onDismiss: () -> Unit = {}
    )
}