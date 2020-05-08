package com.example.employeedata.module.employee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.example.employeedata.Constants
import com.example.employeedata.R
import com.example.employeedata.database.EmployeeDatabaseAdapter
import com.example.employeedata.database.Message
import kotlinx.android.synthetic.main.activity_add_data.*
import kotlinx.android.synthetic.main.toolbar.*

class AddDataActivity : AppCompatActivity(),View.OnClickListener{

    private var employeeData: EmployeeBean? = null
    private var actionScreen: String? = ""
    private lateinit var databaseHelper: EmployeeDatabaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)
        getDataFromIntent()
        initView()
    }

    /**
     * This method is used to init the views and add clicklisteners
     */
    private fun initView() {
        backIv.setOnClickListener(this)
        saveBtn.setOnClickListener(this)
        databaseHelper = EmployeeDatabaseAdapter(this)
        titleTv.setText(getString(R.string.add_data_title))
    }

    /**
     * This method is responsible to get data from intent
     */
    private fun getDataFromIntent() {
        if(intent.extras!!.containsKey(Constants.ACTION)){
            actionScreen =  intent.extras!!.getString(Constants.ACTION)
            Log.e("action_screen",actionScreen!!)
            if(actionScreen.equals(Constants.UPDATE_SCREEN)){
                employeeData =  intent.getSerializableExtra(Constants.EMPLOYEE_DATA) as? EmployeeBean
                setData(employeeData!!)
            }
        }
    }

    /**
     * This method is responsible to set data in ui
     */
    private fun setData(employeeData: EmployeeBean) {
        nameEt.setText(employeeData.name)
        emailEt.setText(employeeData.email)
        ageEt.setText(employeeData.age.toString())
        saveBtn.setText(getString(R.string.update))
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.saveBtn ->{
                if(actionScreen!!.equals(Constants.ADD_SCREEN)){
                    if(dataIsValid()){
                        saveDataInDb()
                    }
                }else{
                    if(dataIsValid())
                        updateInDb()
                }
            }
            R.id.backIv ->{
                finish()
            }
        }
    }

    private fun dataIsValid(): Boolean {
        val name  =    nameEt.text.toString()
        val email  = emailEt.text.toString()
        val age  = ageEt.text.toString()
        if(name.isNullOrEmpty() || email.isNullOrEmpty() || age.isNullOrEmpty()){
            Message.message(this,"Please fill all the fields")
            return false
        }else{
            return  true
        }
    }

    /**
     * This method is responsible to update data in db
     */
    private fun updateInDb() {
        val name  =    nameEt.text.toString()
        val email  = emailEt.text.toString()
        val age  = ageEt.text.toString().toInt()
        val id =  databaseHelper.updateData(name,email,age,employeeData!!.id)
        if(id>0){
            Message.message(this,"Updated successfully")
            finish()
        }else{
            Message.message(this,"error while updating")
        }
    }

    /**
     * This method is used to save data in db
     */
    private fun saveDataInDb() {
        val name  =    nameEt.text.toString()
        val email  = emailEt.text.toString()
        val age  = ageEt.text.toString().toInt()
        val id = databaseHelper.insertData(name,email,age)
        if(id>0){
            Message.message(this,"successfully inserted a row")
            finish()
        }else{
            Message.message(this,"Unsuccessfull")
        }
    }
}
