package com.example.dailytasktracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dailytasktracker.databinding.RecyclerRowBinding
import com.example.dailytasktracker.model.Task

class FavTaskRecyclerAdapter(private var favTaskList: ArrayList<Task>) : RecyclerView.Adapter<FavTaskRecyclerAdapter.FavTaskViewHolder>() {
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

    }

    fun updateFavList(newFavTaskList:List<Task>){
        favTaskList.clear()
        favTaskList.addAll(newFavTaskList)
        notifyDataSetChanged()
    }
}