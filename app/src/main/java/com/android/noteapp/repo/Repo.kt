package com.android.noteapp.repo

import androidx.lifecycle.LiveData
import com.android.noteapp.data.local.Dao
import com.android.noteapp.data.local.NoteDatabase
import com.android.noteapp.data.models.ModelNote
import javax.inject.Inject
//
class Repo @Inject constructor(private val noteDatabase: NoteDatabase) {

    val allNotes: LiveData<List<ModelNote>> = noteDatabase.getDao().getAllNote()


    suspend fun addNote(note: ModelNote) {
        noteDatabase.getDao().addNote(note)
    }

    suspend fun delete(note: ModelNote) {
        noteDatabase.getDao().deleteNote(note)
    }

    suspend fun update(note: ModelNote){
        noteDatabase.getDao().update(note.id,note.noteDescription)
    }

    suspend fun deleteNoteById(id: Int) {
        noteDatabase.getDao().deleteById(id)
    }
}