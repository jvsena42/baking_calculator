package com.bulletapps.candypricer.presentation.ui.widgets

import androidx.annotation.VisibleForTesting
import androidx.fragment.app.FragmentManager
import com.bulletapps.candypricer.presentation.util.DateConstant.BACKEND_FORMAT
import com.bulletapps.candypricer.presentation.util.LOCALE_BR
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

class DatePicker {
    companion object {
        const val ZERO = 0
        const val UTC_TIMEZONE = "UTC"
        const val DATE_FORMAT = BACKEND_FORMAT
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
            .setValidator(DateValidatorPointForward.from(removeTimeFromDate(start)))
            .build()
    }

    private fun buildCalendarUntilConstraints(start: Long): CalendarConstraints {
        return CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.before(removeTimeFromDate(start)))
            .build()
    }

    @VisibleForTesting
    fun buildResult(dateInMillis: Long) = Result(
        presentationDate = formatDate(dateInMillis, builder.presentationDateFormatResult),
        date = formatDate(dateInMillis, builder.dateFormatResult),
        dateInMillis = removeTimeFromDate(dateInMillis)
    )

    private fun formatDate(dateInMillis: Long, format: String) =
        SimpleDateFormat(format, LOCALE_BR)
            .apply { timeZone = getUTCTimezone() }
            .format(Date(dateInMillis))

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

    @Parcelize
    internal class BetweenDateValidator(
        private val start: Long,
        private val end: Long
    ): CalendarConstraints.DateValidator {
        override fun isValid(date: Long) = date in start..end
    }

    data class DatePickerBuilder(
        var title: String = "",
        var presentationDateFormatResult: String = DAY_MONTH_YEAR_FORMAT,
        var dateFormatResult: String = DATE_FORMAT,
        var selectedDate: Long? = null,
        var validDateRange: Pair<Long, Long>? = null,
        var validOnlyDateAfter: Long? = null,
        var validDateUntil: Long? = null,
        var onDateSelect: (Result) -> Unit = {},
        var onCancel: () -> Unit = {},
        var onDismiss: () -> Unit = {}
    )
}