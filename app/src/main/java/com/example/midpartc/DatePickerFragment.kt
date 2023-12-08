package com.example.midpartc

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment

/**
 * Date Picker Fragment
 * For selecting date of birth year, month and day
 */
class DatePickerFragment(val button: Button,
                         var selectedYear: Int,
                         var selectedMonth: Int,
                         var selectedDay: Int,
    ): DialogFragment(), DatePickerDialog.OnDateSetListener {

    // setting current date when displaying date picker fragment
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if(selectedYear != 0 && selectedMonth != 0 && selectedDay != 0) {
            val dialog = DatePickerDialog(requireContext(), this, selectedYear, selectedMonth, selectedDay)
            // disabling future dates for the date picker
            dialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
            return dialog
        } else {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(requireContext(), this, year, month, day)
            // disabling future dates for the date picker
            dialog.datePicker.maxDate = c.timeInMillis
            return dialog
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        selectedYear = year
        selectedMonth = month
        selectedDay = dayOfMonth

        val selectedDateString = "$selectedMonth/$selectedDay/$selectedYear"
        button.text = selectedDateString
    }

}