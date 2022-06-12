package com.example.week2.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.week2.model.DbModel
import com.example.week2.model.Event

class DataBaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists $TABLE_NAME(ID INTEGER PRIMARY KEY autoincrement, $NAME TEXT, $Category TEXT, $Date TEXT);")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(sqLiteDatabase)
    }

    fun insertData(name: String?, category: String?, date: Long?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, name) //instance of content value
        contentValues.put(Category, category)
        contentValues.put(Date, date)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }

    @get:SuppressLint("Range")
    val data: ArrayList<Event>
        get() {
            val list: ArrayList<Event> = ArrayList<Event>()
            val data: Cursor
            val db = this.writableDatabase
            data = db.rawQuery(" SELECT * FROM $TABLE_NAME", null)
            while (data.moveToNext()) {
                val id = data.getInt(data.getColumnIndex(ID))
                val name = data.getString(data.getColumnIndex(NAME))
                val marks = data.getString(data.getColumnIndex(Category))
                val date = data.getLong(data.getColumnIndex(Date))
                list.add(Event(id,name, marks, date))
            }
            return list
        }

    fun update(id: Int, name: String?, marks: String?, grade: String?) {
        val db: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, name)
        values.put(Category, marks)
        values.put(Date, grade)
        db.update(TABLE_NAME, values, "$ID = ?", arrayOf(id.toString()))
    }

    companion object {
        const val DATABASE_NAME = "student.db"
        const val TABLE_NAME = "student"
        const val ID = "ID"
        const val NAME = "NAME"
        const val Category = "CATEGORY"
        const val Date = "DATE"
    }
}