package com.android.noteapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.noteapp.data.models.ModelNote
import com.android.noteapp.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(private val repo: Repo) : ViewModel() {

    private var _message = MutableLiveData<String>()
    val message get() = _message

    val allNote: LiveData<List<ModelNote>>

    init {
        allNote = repo.allNotes
    }

    fun addNote(note: ModelNote) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.addNote(note)
            } catch (e: Exception) {
                _message.postValue(e.message)
            }
        }
    }

    fun deleteNote(note: ModelNote) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.delete(note)
            } catch (e: Exception) {
                _message.postValue(e.message)
            }
        }
    }

    fun deleteNoteById(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.deleteNoteById(id)
            } catch (e: Exception) {
                _message.postValue(e.message)
            }
        }
    }

    fun updateNote(note: ModelNote) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.update(note)
            } catch (e: Exception) {
                _message.postValue(e.message)
            }
        }
    }

}