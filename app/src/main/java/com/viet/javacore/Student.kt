package com.viet.javacore

data class Student
    (var name : String, var yearOfBird : Int,
     var phoneNumber : String, var specialized : String) : Comparable<Student> {

    override fun compareTo(other: Student): Int {
        return this.yearOfBird - other.yearOfBird
    }
}