package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.annotation.VisibleForTesting
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class DatePicker {
    companion object {
        const val ZERO = 0
        const val UTC_TIMEZONE = "UTC"
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val DAY_MONTH_YEAR_FORMAT = "dd/MM/yyy"
    }

    var builder = DatePickerBuilder()

    fun show(fragmentManager: FragmentManager) = MaterialDatePicker.Builder.datePicker()
        .apply {
            setTitleText(builder.title)
            builder.validDateRange?.let {
                val constraints = buildCalendarConstraints(it.first, it.second)
                setCalendarConstraints(constraints)
            }
            builder.validOnlyDateAfter?.let {
                val constraints = buildCalendarConstraints(it)
                setCalendarConstraints(constraints)
            }
            builder.validDateUntil?.let {
                val constraints = buildCalendarConstraints(it)
                setCalendarConstraints(constraints)
            }
            builder.selectedDate?.let { setSelection(it) }
        }
        .build()
        .apply {
            addOnPositiveButtonClickListener { builder.onDateSelect(buildResult(it)) }
            addOnCancelListener { builder.onCancel() }
            addOnDismissListener { builder.onDismiss() }
        }
        .show(fragmentManager, null)


    private fun buildCalendarConstraints(start: Long, end: Long): CalendarConstraints {
        val startWithoutTime = removeTimeFromDate(start)
        val endWithoutTime = if (end < start) startWithoutTime else removeTimeFromDate(end)

        return CalendarConstraints.Builder()
            .setStart(startWithoutTime)
            .setEnd(endWithoutTime)
            .setValidator(BetweenDateValidator(startWithoutTime, endWithoutTime))
            .build()
    }

    private fun buildCalendarConstraints(start: Long): CalendarConstraints {
        return CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward(removeTimeFromDate(start)))
            .build()
    }

    @VisibleForTesting
    internal fun removeTimeFromDate(dateInMillis: Long) =
        Calendar.getInstance(getUTCTimezone()).apply {
            timeInMillis = dateInMillis
            set(Calendar.HOUR_OF_DAY, ZERO)
            set(Calendar.MINUTE, ZERO)
            set(Calendar.SECOND, ZERO)
            set(Calendar.MILLISECOND, ZERO)
        }.timeInMillis

    private fun getUTCTimezone() = TimeZone.getTimeZone(UTC_TIMEZONE)

    data class Result(
        val presentationDate: String,
        val date: String,
        val dateInMillis: Long
    )

    data class DatePickerBuilder(
        var title: String = "",
        var presentationDateFormatResult: String = DAY_MONTH_YEAR_FORMAT,
        var dateFormatResult: String = DATE_FORMAT,
        var selectedDate: Long? = null,
        var validDateRange: Pair<Long, Long>? = null,
        var validOnlyDateAfter: Long? = null,
        var validDateUntil: Long? = null,
        var onDateSelect: (DatePicker.Result) -> Unit = {},
        var onCancel: () -> Unit = {},
        var onDismiss: () -> Unit = {}
    )
}