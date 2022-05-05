package com.example.idrd.presentation.form.model

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.idrd.R
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class DatePicker(val listerner:(day:Int, month:Int, year:Int)->Unit):DialogFragment(),
DatePickerDialog.OnDateSetListener{
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        listerner(day,month,year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c=Calendar.getInstance()
        val day=c.get(Calendar.DAY_OF_MONTH)
        val month=c.get(Calendar.MONTH)
        val year= c.get(Calendar.YEAR)

        val picker=DatePickerDialog(activity as Context, this, year, month, day)
        picker.datePicker.minDate=c.timeInMillis


        return picker
    }
}