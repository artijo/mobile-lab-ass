package com.example.a653380118_9_massage

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Content(val name:String, val bkdate:String, val roomtype:String, val mstype:String, val mstime:Int):
    Parcelable