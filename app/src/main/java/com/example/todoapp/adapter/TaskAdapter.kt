package com.example.todoapp.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    class TaskViewHolder(val itemTaskBinding: ItemTaskBinding): RecyclerView.ViewHolder(itemTaskBinding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = differ.currentList[position]

        holder.itemTaskBinding.taskTV.text = task.task
        holder.itemTaskBinding.checkBoxTask.isChecked = task.isCompleted

        // Apply strike-through and color change
        applyStrikeThrough(holder.itemTaskBinding.taskTV, task.isCompleted)

        // Listen for checkbox changes
        holder.itemTaskBinding.checkBoxTask.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            applyStrikeThrough(holder.itemTaskBinding.taskTV , isChecked)
        }
    }

    private fun applyStrikeThrough(textView: TextView, isChecked: Boolean) {
        if (isChecked){
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textView.setTextColor(Color.GRAY)
        }else{
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            textView.paintFlags = textView.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv() // Remove underline when unchecked
            textView.setTextColor(Color.BLACK)

        }
    }
}