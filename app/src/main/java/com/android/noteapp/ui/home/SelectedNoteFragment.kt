package com.android.noteapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.noteapp.R
import com.android.noteapp.data.models.ModelNote
import com.android.noteapp.databinding.FragmentSelectedNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SelectedNoteFragment : Fragment() {

    private var _binding: FragmentSelectedNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    lateinit var noteEdt: EditText

    var note = ""
    var date = ""
    var id = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSelectedNoteBinding.bind(view)

        val noteType = SelectedNoteFragmentArgs.fromBundle(requireArguments()).noteType

        noteEdt = binding.content

        getData(noteType)

        onClick(noteType)


    }

    private fun getData(noteType: String) {

        if (noteType == "Edit") {

            note = SelectedNoteFragmentArgs.fromBundle(requireArguments()).note
            date = SelectedNoteFragmentArgs.fromBundle(requireArguments()).date
            id = SelectedNoteFragmentArgs.fromBundle(requireArguments()).id.toString()
            noteEdt.setText(note)

            binding.noteDate.text = date
        }
    }

    private fun onClick(noteType: String) {

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

//        binding.content.setOnClickListener {
//            binding.noteText.text = getString(R.string.edit_note)
//            binding.update.visibility = View.VISIBLE
//        }

        binding.delete.setOnClickListener {
            viewModel.deleteNoteById(id.toInt())
            Toast.makeText(requireContext(), "Your Note is Deleted", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()

        }

        binding.update.setOnClickListener() {

            val content = binding.content.text.toString()

            if (noteType == "Edit") {
                if (content.isNotEmpty()) {

                    val myFormat = "dd/MM/yyyy"
                    val simpleDateFormat = SimpleDateFormat(myFormat, Locale.UK)
                    val currentDateAndTime: String = simpleDateFormat.format(Date())

                    binding.noteDate.visibility = View.VISIBLE
                    binding.noteDate.text = currentDateAndTime

                    val updateNote =
                        ModelNote(noteDescription = content, timeStamp = currentDateAndTime)
                    updateNote.id = id.toInt()

                    viewModel.updateNote(updateNote)
                    Toast.makeText(requireContext(), "Updated", Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}