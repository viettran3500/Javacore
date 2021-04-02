package com.viet.javacore

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val PHONE_PATTERN: String = "(0?\\d{9})";
    val YEAR_PATTERN: String = "([1-2]?\\d{3})";
    lateinit var arrayListStudent1 : MutableList<Student>
    var arrayListStudent: ArrayList<Student> = ArrayList()
    var arrayListStudent2: ArrayList<Student> = ArrayList()
    var arrayListStudent3: ArrayList<Student> = ArrayList()
    var arrayListStudent4: ArrayList<Student> = ArrayList()
    var specialized: String = ""
    var index: Int = 0
    var studentAdapter: StudentAdapter = StudentAdapter(this@MainActivity, arrayListStudent)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listview.adapter = studentAdapter

        val list: List<String> = listOf("Cao đẳng", "Đại học")
        spinnerSpecialized.adapter =
            ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, list)

        spinnerSpecialized.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0 != null) {
                    specialized = p0.adapter.getItem(p2) as String
                }
            }

        })

        listview.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var student: Student = arrayListStudent.get(p2)
                edittextName.setText(student.name)
                edittextYear.setText(student.yearOfBird.toString())
                edittextPhoneNumber.setText(student.phoneNumber)
                if (student.specialized.equals("Cao đẳng")) {
                    spinnerSpecialized.setSelection(0)
                } else {
                    spinnerSpecialized.setSelection(1)
                }
                index = p2
                buttonEdit.isEnabled = true
            }
        })

        listview.setOnItemLongClickListener(object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ): Boolean {
                deleteItem(p2);
                return false
            }
        })

        buttonAdd.setOnClickListener {
            add()
        }

        buttonEdit.setOnClickListener {
            edit()
        }

        checkboxCD.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                changeCheckbox(p1, "Cao đẳng");
            }
        })

        checkboxDH.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                changeCheckbox(p1, "Cao đẳng");
            }
        })

        buttonSearch.setOnClickListener {
            if (!edittextSearch.text.isEmpty()) {
                search();
            } else {
                Toast.makeText(getApplicationContext(), "must not be left blank", Toast.LENGTH_LONG)
                    .show();
            }
        }
        radioName.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1 == true){
                    arrayListStudent1 = arrayListStudent
                    arrayListStudent1.sortBy { it.name }
                    arrayListStudent = arrayListStudent1 as ArrayList<Student>
                    studentAdapter.notifyDataSetChanged()
                }
            }

        })

        radioYear.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1 == true){
                    arrayListStudent1 = arrayListStudent
                    arrayListStudent1.sortBy { it.yearOfBird }
                    arrayListStudent = arrayListStudent1 as ArrayList<Student>
                    studentAdapter.notifyDataSetChanged()
                }
            }

        })
        radioNum.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if(p1 == true){
                    arrayListStudent1 = arrayListStudent
                    arrayListStudent1.sortBy { it.phoneNumber }
                    arrayListStudent = arrayListStudent1 as ArrayList<Student>
                    studentAdapter.notifyDataSetChanged()
                }
            }

        })

    }

    private fun changeCheckbox(b: Boolean, str: String) {
        if (str.equals("Đại học")) {
            if (b == true) {
                arrayListStudent.addAll(arrayListStudent2);
                studentAdapter.notifyDataSetChanged();
                arrayListStudent2.clear();
            } else {
                for (i in 0 until arrayListStudent.size) {
                    if (arrayListStudent.get(i).specialized.equals("Đại học")) {
                        arrayListStudent2.add(arrayListStudent.get(i));
                    }
                }
                arrayListStudent.removeAll(arrayListStudent2);
                studentAdapter.notifyDataSetChanged();
            }
        }
        if (str.equals("Cao đẳng")) {
            if (b == true) {
                arrayListStudent.addAll(arrayListStudent3);
                studentAdapter.notifyDataSetChanged();
                arrayListStudent3.clear();
            } else {
                for (i in 0 until arrayListStudent.size) {
                    if (arrayListStudent.get(i).specialized.equals("Cao đẳng")) {
                        arrayListStudent3.add(arrayListStudent.get(i));
                    }
                }
                arrayListStudent.removeAll(arrayListStudent3);
                studentAdapter.notifyDataSetChanged();
            }
        }
    }

    private fun deleteItem(i: Int) {
        var dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setTitle("Thông báo")
        dialog.setMessage("Bạn có muốn xóa? ")
        dialog.setPositiveButton("Có", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                arrayListStudent.removeAt(p1);
                studentAdapter.notifyDataSetChanged();
            }
        })
        dialog.setPositiveButton("Không", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
            }
        })
        dialog.show()
    }

    private fun search() {
        arrayListStudent.addAll(arrayListStudent4);
        arrayListStudent4.clear();
        var str: String = edittextSearch.getText().toString().toUpperCase()
        for (i in 0 until arrayListStudent.size) {
            if (!(arrayListStudent.get(i).name.toUpperCase().contains(str) ||
                        arrayListStudent.get(i).yearOfBird.toString().contains(str) ||
                        arrayListStudent.get(i).phoneNumber.toUpperCase().contains(str)||
                        arrayListStudent.get(i).specialized.toUpperCase().contains(str))
            ) {
                arrayListStudent4.add(arrayListStudent.get(i));
            }
        }
        arrayListStudent.removeAll(arrayListStudent4);
        studentAdapter.notifyDataSetChanged();
    }

    private fun edit() {
        if (check()) {
            var name: String = edittextName.text.toString()
            var year: Int = edittextYear.text.toString().toInt()
            var num: String = edittextPhoneNumber.text.toString()
            arrayListStudent.set(index, Student(name, year, num, specialized))
            Collections.sort(arrayListStudent)
            studentAdapter.notifyDataSetChanged()
        }
    }

    private fun add() {
        if (check()) {
            var name: String = edittextName.text.toString()
            var year: Int = edittextYear.text.toString().toInt()
            var num: String = edittextPhoneNumber.text.toString()
            arrayListStudent.add(Student(name, year, num, specialized))
            Collections.sort(arrayListStudent)
            studentAdapter.notifyDataSetChanged()
        }
    }

    private fun check(): Boolean {
        if (edittextPhoneNumber.getText().toString().isEmpty() || edittextName.getText().toString()
                .isEmpty()
            || edittextYear.getText().toString().isEmpty()
        ) {
            Toast.makeText(getApplicationContext(), "must not be left blank", Toast.LENGTH_LONG)
                .show();
            return false;
        }
        var pattern: Pattern = Pattern.compile(PHONE_PATTERN);
        var matcher: Matcher = pattern.matcher(edittextPhoneNumber.getText().toString());
        if (!matcher.matches()) {
            Toast.makeText(getApplicationContext(), "Wrong phone number format", Toast.LENGTH_LONG)
                .show();
            return false;
        }

        var pattern2: Pattern = Pattern.compile(YEAR_PATTERN);
        var matcher2: Matcher = pattern2.matcher(edittextYear.getText().toString());
        if (!matcher2.matches()) {
            Toast.makeText(getApplicationContext(), "Wrong year of bird format", Toast.LENGTH_LONG)
                .show();
            return false;
        }

        for (i in 0 until arrayListStudent.size) {
            if (arrayListStudent.get(i).phoneNumber.equals(
                    edittextPhoneNumber.getText().toString()
                )
            ) {
                Toast.makeText(
                    getApplicationContext(),
                    "Phone number already exists",
                    Toast.LENGTH_LONG
                ).show();
                return false;
            }
        }
        return true;
    }
}
