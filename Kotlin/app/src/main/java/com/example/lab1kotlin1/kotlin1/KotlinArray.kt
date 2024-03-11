package com.example.lab1kotlin1.kotlin1

fun main() {
//    val subjectScore = arrayOf(67,52,73,85,42,78)
//    println("There are "+ subjectScore.size + " subjects in array.")
//    calculateGrade(subjectScore)

    val myScore = arrayOf(48,65,71,81,56)
    CalculateGPAX(myScore)
}
fun calculateGrade(scoreArr: Array<Int>){
    var i : Int = 1
    var grade : String
    for (value in scoreArr){
        grade = when {
            value <50 -> "F"
            value <55 -> "D"
            value <60 -> "D+"
            value <65 -> "C"
            value <70 -> "C+"
            value <75 -> "B"
            value <80 -> "B+"
            else -> "A"
        }
        println(" Grade of Subject Number $i : $value = $grade")
        i++
    }
}
fun CalculateGPAX(score: Array<Int>){
    val credit = score.size*3
    var gpa : Double = 0.0
    var grade : Double
    var g : String

    var arr = Array<Double>(score.size) { 0.0 }
    var arrIndex : Int = 0

    for (value in score){
        var i : Int =1
        g = when {
            value <50 -> "F"
            value <55 -> "D"
            value <60 -> "D+"
            value <65 -> "C"
            value <70 -> "C+"
            value <75 -> "B"
            value <80 -> "B+"
            else -> "A"
        }
        grade = when {
            value <50 -> 0.0
            value <55 -> 1.0
            value <60 -> 1.5
            value <65 -> 2.0
            value <70 -> 2.5
            value <75 -> 3.0
            value <80 -> 3.5
            else -> 4.0
        }
        arr[arrIndex] = grade
        arrIndex++
        println(" Grade of Subject Number $i : $value = $g : $grade")
        i++
        gpa += grade*3
    }
    val gpax = gpa/credit
    print("GPAX = (")
    for(i in 0 until arr.size - 1){
        print("($i*3) + ")
    }
    print("(${arr.last()}*3)) / $credit = $gpax")
}