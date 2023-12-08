package com.example.midpartc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast

/**
 * Employee Detail Form
 */
class EmployeeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)

        // getting shared preferences instance
        val sharedPref = getPreferences(Context.MODE_PRIVATE)

        // finding all view
        val nameEditText = findViewById<EditText>(R.id.name_field)
        val ageEditText = findViewById<EditText>(R.id.age_field)
        val maleRadioButton = findViewById<RadioButton>(R.id.male)
        val femaleRadioButton = findViewById<RadioButton>(R.id.female)
        val datePickerButton = findViewById<Button>(R.id.date_button)
        val departmentSpinner = findViewById<Spinner>(R.id.departments)
        val saveButton = findViewById<Button>(R.id.save_button)

        // reading values from shared preferences
        var selectedGender = sharedPref.getString(getString(R.string.gender_key), "")
        var selectedDOBYear = sharedPref.getInt(getString(R.string.birth_year_key), 0)
        var selectedDOBMonth = sharedPref.getInt(getString(R.string.birth_month_key), 0)
        var selectedDOBDay = sharedPref.getInt(getString(R.string.birth_day_key), 0)
        var selectedDepartment = sharedPref.getString(getString(R.string.department_key), "")

        // for getting previous selected department index
        var selectedDepartmentPosition = 0

        // setting name edit text's text from the shared preferences
        nameEditText.setText(sharedPref.getString(getString(R.string.name_key), ""))
        // setting age edit text's text from the shared preferences
        ageEditText.setText(sharedPref.getString(getString(R.string.age_key), ""))

        // checking if selected gender from shared preferences is null or not
        // if it is not null then setting it to radio button
        if(selectedGender != null) {
            if(selectedGender == "Male") {
                maleRadioButton.isChecked = true
            } else {
                femaleRadioButton.isChecked = true
            }
        }

        // checking if year, month and day read from shared preferences are not zero
        // if not then changing button text to stored date of birth
        if(selectedDOBYear != 0 || selectedDOBMonth != 0 || selectedDOBDay != 0) {
            val selectedDateString = "$selectedDOBMonth/$selectedDOBDay/$selectedDOBYear"
            datePickerButton.text = selectedDateString
        }

        maleRadioButton.setOnClickListener {
            selectedGender = "Male"
        }

        femaleRadioButton.setOnClickListener {
            selectedGender = "Female"
        }

        // creating date picker fragment instance
        val dateFragment = DatePickerFragment(datePickerButton, selectedDOBYear, selectedDOBMonth, selectedDOBDay)

        // showing date picker fragment on select date button
        datePickerButton.setOnClickListener {
            dateFragment.show(supportFragmentManager, "datePicker")
        }

        // creating adapter from string array of departments defined in resources
        // attaching it to spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.departments,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            departmentSpinner.adapter = adapter
            selectedDepartmentPosition = adapter.getPosition(selectedDepartment)
        }

        // creating department activity for spinner
        val departmentActivity = DepartmentActivity(selectedDepartment!!)

        // attaching activity to spinner
        departmentSpinner.onItemSelectedListener = departmentActivity
        departmentSpinner.setSelection(selectedDepartmentPosition)

        // validating user inputs
        // displaying error via Toast
        fun validateInputs(): Boolean {
            if (nameEditText.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Name.", Toast.LENGTH_SHORT).show()
                return false
            } else if (ageEditText.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter Age.", Toast.LENGTH_SHORT).show()
                return false
            } else if (dateFragment.selectedYear == 0 || dateFragment.selectedMonth == 0 || dateFragment.selectedDay == 0) {
                Toast.makeText(this, "Please select valid Date of Birth.", Toast.LENGTH_SHORT).show()
                return false
            } else if (departmentActivity.selectedDepartment.isEmpty()) {
                Toast.makeText(this, "Please select a Department.", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }

        // validating inputs on save button click
        // if validated successfully then saving it to shared preferences and displaying toast
        saveButton.setOnClickListener {
            if(validateInputs()) {
                with(sharedPref.edit()) {
                    putString(getString(R.string.name_key), nameEditText.text.toString())
                    putString(getString(R.string.age_key), ageEditText.text.toString())
                    putString(getString(R.string.gender_key), selectedGender)
                    putInt(getString(R.string.birth_year_key), dateFragment.selectedYear)
                    putInt(getString(R.string.birth_month_key), dateFragment.selectedMonth)
                    putInt(getString(R.string.birth_day_key), dateFragment.selectedDay)
                    putString(getString(R.string.department_key), departmentActivity.selectedDepartment)
                    apply()
                }
                Toast.makeText(this, "Details are Saved!.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}