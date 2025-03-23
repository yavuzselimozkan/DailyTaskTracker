package com.example.dailytasktracker.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailytasktracker.R
import com.example.dailytasktracker.databinding.RecyclerRowBinding
import com.example.dailytasktracker.model.Task

class FavTaskRecyclerAdapter(
    private var favTaskList: ArrayList<Task>,
    private val toggleCompleteTask: (id:Int) -> (Unit) //viewModel'in fonksiyonu kullanılacak
) : RecyclerView.Adapter<FavTaskRecyclerAdapter.FavTaskViewHolder>() {

    class FavTaskViewHolder(var binding:RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavTaskViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavTaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favTaskList.size
    }

    override fun onBindViewHolder(holder: FavTaskViewHolder, position: Int) {
        holder.binding.taskNameRow.text = favTaskList[position].taskName
        holder.binding.taskDescRow.text = favTaskList[position].taskDesc

        holder.binding.imgCheck.setOnClickListener {
            toggleCompleteTask(favTaskList[position].taskId)
        }

        if(favTaskList[position].isComplete){
            holder.binding.imgCheck.setImageResource(R.drawable.checked_button)
            holder.binding.taskNameRow.paintFlags = holder.binding.taskNameRow.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.taskDescRow.paintFlags = holder.binding.taskDescRow.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            holder.binding.imgCheck.setImageResource(R.drawable.unchecked_button)
            holder.binding.taskNameRow.paintFlags = holder.binding.taskNameRow.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.binding.taskDescRow.paintFlags = holder.binding.taskDescRow.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.itemView.setOnClickListener {
            println("Task ${favTaskList[position].isComplete}")
        }

    }

    fun updateFavList(newFavTaskList:List<Task>){
        favTaskList.clear()
        favTaskList.addAll(newFavTaskList)
        notifyDataSetChanged()
    }
}