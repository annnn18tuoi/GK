package com.example.gk

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.content.LocusId
import android.database.Cursor

class DatabaseHelper (context: Context):
    SQLiteOpenHelper(context, "SV",null,1){


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table SV(id INTEGER primary key "+"autoincrement,name text,phone text,type text)")
    }

    override fun onUpgrade(db: SQLiteDatabase,oldVersion: Int,newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS" + "SV")
        onCreate(db)
    }
    fun insertData(name: String,phone:String,marks: String){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2,name)
        contentValues.put(COL_3,phone)
        contentValues.put(COL_4,marks)
        db.insert("SV",null,contentValues)
    }
    fun updateData(id: String, name: String, phone:String, marks: String):
            Boolean{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, phone)
        contentValues.put(COL_4, marks)
        db.update("SV",contentValues,"id = ?", arrayOf(id))
        return true
    }
    fun deleteData(id:String):Int{
        val db = this.writableDatabase
        return db.delete("SV"," id = ?", arrayOf(id))
    }
    val allData : Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM " + "SV", null)
            return res
        }

    companion object{
        val COL_1 ="ID"
        val COL_2 ="Name"
        val COL_3 ="Phone"
        val COL_4 ="Type"
    }
}
