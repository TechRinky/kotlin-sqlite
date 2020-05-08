package com.example.employeedata.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.employeedata.module.employee.EmployeeBean

class EmployeeDatabaseAdapter(context: Context){
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "EmployeeDatabase"
        private val TABLE_NAME = "EmployeeTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"
        private  val KEY_AGE = "age"
    }
    private val mContext = context
    private val databaseHelper = DatabaseHelper(mContext)
    private val employeeList= ArrayList<EmployeeBean>()

    fun insertData(name:String,email: String,age : Int): Long {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_EMAIL,email)
        contentValues.put(KEY_AGE, age)
        val id = db.insert(TABLE_NAME,null,contentValues)
        return id
    }

    fun updateData(name: String,email: String,age: Int,id: Int): Int {
        val db = databaseHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_EMAIL,email)
        contentValues.put(KEY_AGE, age)
        val WhereArgs = arrayOf<String>(id.toString())
        val updatedRows = db.update(TABLE_NAME,contentValues, KEY_ID+" =?" ,WhereArgs)
        return updatedRows
    }

    fun getAllData(): ArrayList<EmployeeBean> {
        employeeList.clear()
        val db =  databaseHelper.writableDatabase
        val columns = arrayOf<String>(KEY_ID,KEY_NAME, KEY_EMAIL, KEY_AGE)
        val cursor: Cursor = db.query(TABLE_NAME,columns,null,null,null,null,null)
        while (cursor.moveToNext()){
            val employeeBean = EmployeeBean()
            val index1 =  cursor.getColumnIndex(KEY_NAME)
            val index2 =  cursor.getColumnIndex(KEY_EMAIL)
            val index3 =  cursor.getColumnIndex(KEY_AGE)
            val index4 =  cursor.getColumnIndex(KEY_ID)
            employeeBean.name = cursor.getString(index1)
            employeeBean.email  = cursor.getString(index2)
            employeeBean.age = cursor.getInt(index3)
            employeeBean.id = cursor.getInt(index4)
            employeeList.add(employeeBean)
        }
        return employeeList
    }

    class DatabaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

        private  val mContext = context
        override fun onCreate(db: SQLiteDatabase?) {
            val querry = "CREATE TABLE "+TABLE_NAME+" ("+ KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" VARCHAR(255), "+ KEY_EMAIL+" TEXT, "+ KEY_AGE+" INTEGER);"
            db?.execSQL(querry)
            if(db!=null){
                Message.message(mContext,"oncreate db success" )
            }
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
            onCreate(db)
        }

    }

}