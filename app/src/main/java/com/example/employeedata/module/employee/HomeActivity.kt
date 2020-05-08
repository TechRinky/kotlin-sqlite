package com.example.employeedata.module.employee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeedata.Constants
import com.example.employeedata.OnItemClickListener
import com.example.employeedata.R
import com.example.employeedata.database.EmployeeDatabaseAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeActivity : AppCompatActivity(),View.OnClickListener, OnItemClickListener {
    private lateinit var databaseHelper: EmployeeDatabaseAdapter
    private var employeeList= ArrayList<EmployeeBean>()
    private lateinit var employeeAdapter : EmployeeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
    }

    /**
     * This method is used to set views and clicklistener
     */
    private fun initView() {
        backIv.setImageResource(R.drawable.menu_icon)
        titleTv.setText(getString(R.string.employee_title))
        setClickListener()
        databaseHelper = EmployeeDatabaseAdapter(this)
        employeeAdapter = EmployeeAdapter(employeeList)
        employeeAdapter.setOnClickListener(this)
        employeeRecylerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        employeeRecylerview.adapter = employeeAdapter
    }

    private fun setClickListener() {
        backIv.setOnClickListener(this)
        addDataTv.setOnClickListener(this)
    }

    /**
     * This method is used to get data from db
     */
    private fun getDataFromDb() {
        employeeList.clear()
        employeeList.addAll( databaseHelper.getAllData())
        showData()
    }

    /**
     * This method will show data when list is not empty otherwise no data found ui will be shown
     */
    private fun showData() {
        if(employeeList.size == 0){
            noDataFoundTv.visibility=View.VISIBLE
        }else{
            employeeAdapter.notifyDataSetChanged()
            noDataFoundTv.visibility=View.GONE
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.addDataTv ->{
                goToAddDataActivity(-1)
            }
            R.id.backIv ->{
                openDrawer()
            }
        }

    }

    /**
     * This method is used to open drawer
     */
    private fun openDrawer() {
        drawerLayout.openDrawer(addDataTv)

    }

    /**
     * This method is used to navigate to Add Data activity
     */
    private fun goToAddDataActivity(position: Int) {
        val intent = Intent(this,AddDataActivity::class.java)
        if(position == -1){
            intent.putExtra(Constants.ACTION,Constants.ADD_SCREEN)
        }else{
            intent.putExtra(Constants.ACTION,Constants.UPDATE_SCREEN)
            intent.putExtra(Constants.EMPLOYEE_DATA,employeeList.get(position))
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        getDataFromDb()
    }

    override fun itemClickListener(view: View, position: Int) {
       goToAddDataActivity(position)
    }

}
