package com.example.lab11

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {
    companion object {
        val DATABASE_NAME = "StudentDB"
        val DB_VERSION = 1
        val TABLE_NAME = "Student"
        val COLUMN_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_GEMDER = "gender"
        val COLUMN_AGE = "age"
        private val sqliteHelper: DatabaseHelper? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseHelper {
            if (sqliteHelper == null) {
                return DatabaseHelper(context.applicationContext)
            }
            return sqliteHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID TEXT PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_GEMDER TEXT," +
                "$COLUMN_AGE INTEGER" +
                ")"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getAllStudent(): ArrayList<Student> {
        val stdList = ArrayList<Student>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        var cursor : Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException){
            onCreate(db)
            return ArrayList()
        }
        var id :String
        var name :String
        var gender: String
        var age: Int

        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast){
                id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                gender = cursor.getString(cursor.getColumnIndex(COLUMN_GEMDER))
                age = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE))
                stdList.add(Student(id,name,gender,age))
                cursor.moveToNext()
            }
        }

        cursor.close()
        return stdList
    }

    @SuppressLint("Range")
    fun searchStudent(std_id: String): Student? {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        var cursor: Cursor? = null
        var std: Student? = null
        try {
            cursor = db.rawQuery(selectQuery, arrayOf(std_id))
            if (cursor.moveToFirst()) {
                val id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val gender = cursor.getString(cursor.getColumnIndex(COLUMN_GEMDER))
                val age = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE))
                std = Student(id, name, gender, age)
            }
        } catch (e: SQLiteException) {
            onCreate(db)
            return null
        }
        cursor.close()
        return std
    }


    fun insertStudent(std: Student): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, std.std_id)
        values.put(COLUMN_NAME, std.std_name)
        values.put(COLUMN_GEMDER, std.std_gender)
        values.put(COLUMN_AGE, std.std_age)
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return success
    }

    fun updateStudent(std: Student): Int {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, std.std_name)
        values.put(COLUMN_GEMDER, std.std_gender)
        values.put(COLUMN_AGE, std.std_age)
        val success = db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(std.std_id))
        db.close()
        return success
    }

    fun deleteStudent(std_id: String): Int {
        val db = writableDatabase
        val success = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(std_id))
        db.close()
        return success
    }
}