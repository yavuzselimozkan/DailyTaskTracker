package com.example.dailytasktracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailytasktracker.databinding.RecyclerRowBinding
import com.example.dailytasktracker.model.Task

class TaskRecyclerAdapter(private var taskList : ArrayList<Task>) : RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder>() {
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
    }

    fun updateTaskList(newTaskList :List<Task>)
    {
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }
}