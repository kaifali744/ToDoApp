package com.example.todoapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.view.inputmethod.EditorInfo
import com.example.todoapp.databinding.FragmentFirstBinding
import com.example.todoapp.model.Task
import com.example.todoapp.viewmodel.TaskViewModel
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FirstFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFirstBinding? = null

    lateinit var taskViewModel: TaskViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheet)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskViewModel = (activity as MainActivity).taskViewModel

        binding.saveBtn.setOnClickListener {
            saveTask()
        }

        binding.closeBtn.setOnClickListener {
            dismiss()
        }

        binding.taskEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                // Hide the keyboard when the Enter key (or Done) is pressed
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)

                // Prevent the Enter key from moving to the next line
                true  // Indicate that the event is consumed
            } else {
                false  // Let other actions be handled normally
            }
        }


    }

    private fun saveTask() {
        val taskIn = binding.taskEt.text.toString().trim()

        if (taskIn.isNotEmpty()){
            val task = Task(0, taskIn)
            taskViewModel.insertTask(task)
            dismiss()
            Toast.makeText(context, "Task Added", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Enter task!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}