package com.android.noteapp.ui.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.noteapp.R
import com.android.noteapp.data.models.ModelNote
import com.android.noteapp.databinding.FragmentAddNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddNoteBinding.bind(view)

        getDate()
        onClick()
    }

    private fun getDate() {

        val myFormat = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(myFormat, Locale.UK)
        val currentDateAndTime: String = simpleDateFormat.format(Date())

        binding.noteDate.text = currentDateAndTime
    }

    private fun onClick() {

        binding.add.setOnClickListener {

            val content = binding.content.text.toString()

            if (content.isNotEmpty()) {

                val myFormat = "dd/MM/yyyy"
                val simpleDateFormat = SimpleDateFormat(myFormat, Locale.UK)
                val currentDateAndTime: String = simpleDateFormat.format(Date())

                viewModel.addNote(ModelNote(content, currentDateAndTime))
                Toast.makeText(requireContext(), "Added", Toast.LENGTH_LONG).show()

                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}