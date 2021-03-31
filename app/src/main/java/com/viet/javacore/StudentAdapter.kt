package com.viet.javacore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.FieldPosition

class StudentAdapter (var context : Context, var arrayListStudent : ArrayList<Student>) : BaseAdapter() {

    class ViewHolder(row : View){
        var textviewName : TextView
        var textviewYear : TextView
        var textviewNum : TextView
        var textviewSpecialized : TextView

        init {
            textviewName = row.findViewById(R.id.tetxviewName)
            textviewYear = row.findViewById(R.id.tetxviewYear)
            textviewNum = row.findViewById(R.id.tetxviewNum)
            textviewSpecialized = row.findViewById(R.id.tetxviewSpecialized)

        }
    }

    override fun getView(position : Int, convertview: View?, p2: ViewGroup?): View {
        var view : View?
        var viewHolder : ViewHolder
        if(convertview == null){
            var layoutInflater : LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.student, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertview
            viewHolder = convertview.tag as ViewHolder
        }
        var student : Student = getItem(position) as Student
        viewHolder.textviewName.text = student.name
        viewHolder.textviewYear.text = student.yearOfBird.toString()
        viewHolder.textviewNum.text = student.phoneNumber
        viewHolder.textviewSpecialized.text = student.specialized
        return view as View
    }

    override fun getItem(p0: Int): Any {
        return arrayListStudent.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return arrayListStudent.size
    }
}