package com.example.midpartc

import android.app.Activity
import android.view.View
import android.widget.AdapterView

/**
 * Getting the selected department from the Spinner
 */
class DepartmentActivity(var selectedDepartment: String): Activity(), AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedDepartment = parent!!.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}