package com.android.noteapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView.OnQueryTextListener
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.noteapp.R
import com.android.noteapp.adapters.NoteAdapter
import com.android.noteapp.data.models.ModelNote
import com.android.noteapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), PopupMenu.OnMenuItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adapter: NoteAdapter by lazy { NoteAdapter() }

    private val viewModel: HomeViewModel by viewModels()

    lateinit var selectedNote: ModelNote

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.recycle.adapter = adapter

        binding.search.setOnQueryTextListener(object : OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                if (p0 != null) {
                    adapter.filterList(p0)
                }
                return true
            }
        })


//        if (adapter.list.size == null){
//
//            binding.recycle.visibility = View.GONE
//            binding.textWarning.visibility = View.VISIBLE
//
//        }else{
//
//            binding.recycle.visibility = View.VISIBLE
//            binding.textWarning.visibility = View.GONE
//        }


        onClick()
        initUI()
        observe()

    }

    private fun observe() {

        viewModel.allNote.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.updateList(list)
            }
        })
    }

    private fun initUI() {

        binding.recycle.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)

    }

    private fun onClick() {

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }


        adapter.onClick = object : NoteAdapter.OnClick {

            override fun onClick(note: ModelNote) {

                findNavController().navigate(HomeFragmentDirections
                    .actionHomeFragmentToSelectedNoteFragment(
                        note.noteDescription,
                        note.timeStamp,
                        note.id,
                        noteType = "Edit"
                    ))
            }

            override fun onLongClick(note: ModelNote, cardView: CardView) {

                selectedNote = note
                popUpDisplay(cardView)

            }
        }
    }

    private fun popUpDisplay(cardView: CardView) {

        val popUp = PopupMenu(requireContext(), cardView)
        popUp.setOnMenuItemClickListener(this@HomeFragment)
        popUp.inflate(R.menu.pop_up_menu)
        popUp.show()
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {

        if (p0?.itemId == R.id.delete_note) {

            viewModel.deleteNote(selectedNote)

            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

