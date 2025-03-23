package com.example.dailytasktracker.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dailytasktracker.R
import com.example.dailytasktracker.databinding.RecyclerRowBinding
import com.example.dailytasktracker.model.Task
import com.example.dailytasktracker.view.HomeFragmentDirections

class TaskRecyclerAdapter(
    private var taskList : ArrayList<Task>,
    private val toggleComplete: (id:Int) ->(Unit)
) : RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder>() {

    class TaskViewHolder(var binding:RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.taskNameRow.text = taskList[position].taskName
        holder.binding.taskDescRow.text = taskList[position].taskDesc

        holder.binding.imgCheck.setOnClickListener {
            toggleComplete(taskList[position].taskId)
        }

        //TODO ARD ARDA TIKLAYINCA HATA ALINIYOR.
        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToTaskDetailFragment(taskList[position].taskId)
            Navigation.findNavController(it).navigate(action)
            println("Task complete: "+taskList[position].isComplete)
        }

        if(taskList[position].isComplete){
            holder.binding.imgCheck.setImageResource(R.drawable.checked_button)
            holder.binding.taskNameRow.paintFlags = holder.binding.taskNameRow.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.taskDescRow.paintFlags = holder.binding.taskDescRow.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            holder.binding.imgCheck.setImageResource(R.drawable.unchecked_button)
            holder.binding.taskNameRow.paintFlags = holder.binding.taskNameRow.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.binding.taskDescRow.paintFlags = holder.binding.taskDescRow.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

    }

    fun updateTaskList(newTaskList :List<Task>)
    {
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }
}