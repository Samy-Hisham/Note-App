package com.android.noteapp.ui.selectednote

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
class SelectedNoteViewModel
@Inject
constructor(private val repo: Repo) : ViewModel() {

    private var _message = MutableLiveData<String>()
    val message get() = _message

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