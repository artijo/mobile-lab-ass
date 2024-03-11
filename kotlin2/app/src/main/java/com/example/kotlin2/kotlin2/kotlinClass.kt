package com.example.kotlin2.kotlin2

data class Subject(val id: String, val name: String, val credit: Int)

open class Person(val fName: String, val lName: String, val deptName: String){
    val firstName: String = fName.replaceFirstChar { it.uppercase() }
    val lastName: String = lName.replaceFirstChar { it.uppercase() }
    val department = "$deptName, College of Computing"

    open fun showDetail(){
        println("$firstName is at $department.")
    }
    companion object {
        fun showCompanion(first_Name:String, last_Name:String ,age:Int){
            println("Person is called from companion object : $first_Name $last_Name is $age years old.")
        }
    }
}

class Teacher(fName: String, lName: String, deptName: String, year: Int): Person(fName, lName, deptName){
    private var salary : Int = 0
    private val yearClass: Int = year
    private var creditClass : Int = 0

    override fun showDetail() {
        println("$firstName is a teacher for $yearClass years at $department.")
    }
    fun calSalary () {
        when {
            yearClass < 5 -> salary = 25000 + (2000 * yearClass)
            yearClass < 10 -> salary = 36000 + (2000 * (yearClass - 5))
            yearClass < 15 -> salary = 47000 + (2000 * (yearClass - 10))
            yearClass < 20 -> salary = 58000 + (2000 * (yearClass - 15))
            else -> salary = 60000 + (2000 * (yearClass - 20))
        }
        println("$firstName's salary is $salary baht")
    }
    fun teach(subj : Subject){
        println(subj.toString())
        creditClass += subj.credit
    }
    fun displayCredit(){
        println("$firstName teaches $creditClass credits.")
    }
}

object Singleton_Person {
    val first_Name = "David"
    val last_Name = "Bowie"
    var age = 23
    fun showCompanion(){
        println("Person is called from singeton object : $first_Name $last_Name is $age years old.")
    }
}

class Student (fName: String, lName: String, deptName: String): Person(fName, lName, deptName){
    var creditTotal : Int = 0
    var gradeTotal : Double = 0.0
    override fun showDetail() {
        println("$firstName is a Student at $department.")
    }
    fun gradeEnroll(subj : Subject, point: Int){
        creditTotal += subj.credit

        var grade : String = when {
            point <50 -> "F"
            point <55 -> "D"
            point <60 -> "D+"
            point <65 -> "C"
            point <70 -> "C+"
            point <75 -> "B"
            point <80 -> "B+"
            else -> "A"
        }
        gradeTotal += when {
            point <50 -> 0.0
            point <55 -> 1.0
            point <60 -> 1.5
            point <65 -> 2.0
            point <70 -> 2.5
            point <75 -> 3.0
            point <80 -> 3.5
            else -> 4.0
        } * subj.credit
        println("Subject(id=${subj.id}, name=${subj.name}, credit=${subj.credit}) Score : $point, Grade : $grade")
    }
    fun displayGpa(){
        println("$firstName's GPA is ${String.format("%.2f",gradeTotal/creditTotal)}.")
    }
}

fun main() {
//    var person1 = Person("Alice","Wonderland","Computer Science")
//    person1.showDetail()
//    println()
//    println("Member NO 2 :")
//    Person.showCompanion("Bobby","Brown",25)
//    println()
//
    var subject1 = Subject("SC362007","Mobile Device Programming",3)
    var subject2 = Subject("SC362005","Database Analysis and Design",3)
    var subject3 = Subject("SC361003","Object Oriented Concepts and Programming",1)
//
//    var person2 = Teacher("Chris","Evans","Information Technology",25)
//    println("Member NO3 : "+person2.firstName + " "+person2.lastName)
//    person2.showDetail()
//    person2.calSalary()
//    println(person2.firstName+" teaches: ")
//    person2.teach(subject1)
//    person2.teach(subject2)
//    person2.teach(subject3)
//    person2.displayCredit()
//    println()
//
//    println("Member NO 4 :")
//    Singleton_Person.showCompanion()
    var person3 = Student("Grace","Moore","Information Technology")
    println("Member NO 5 : ${person3.firstName} ${person3.lastName}")
    person3.showDetail()
    person3.gradeEnroll(subject1, 65)
    person3.gradeEnroll(subject2, 73)
    person3.gradeEnroll(subject3, 90)
    person3.displayGpa()
}