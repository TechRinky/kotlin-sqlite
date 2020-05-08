package com.example.employeedata.module.employee

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employeedata.OnItemClickListener
import com.example.employeedata.R
import kotlinx.android.synthetic.main.employee_row.view.*

class EmployeeAdapter(data:ArrayList<EmployeeBean>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context
    private var employeeList = data
    private lateinit var clickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.employee_row,parent,false)
        return  EmployeeViewHolder(view)
    }

    override fun getItemCount(): Int {
       return employeeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is EmployeeViewHolder){
            val item= employeeList[position]
            holder.nameTv.setText(item.name)
            holder.ageTv.setText(item.age.toString())
            holder.email.setText(item.email)
        }
    }

    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val nameTv = itemView.nameTv
        val  ageTv = itemView.ageTv
        val email = itemView.emailTv
        val container = itemView.rowContainer.setOnClickListener(this)
        override fun onClick(view: View?) {
            clickListener.itemClickListener(view!!,position)
        }
    }

    fun setOnClickListener(listener : OnItemClickListener){
        clickListener = listener
    }
}