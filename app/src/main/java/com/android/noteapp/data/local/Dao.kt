package com.android.noteapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.android.noteapp.data.models.ModelNote

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: ModelNote)

    @Query("SELECT * FROM ModelNote order by id ASC")
    fun getAllNote(): LiveData<List<ModelNote>>

    @Query("UPDATE ModelNote set  description = :note where id = :id")
    suspend fun update(id: Int?,note: String?)

    @Delete
    suspend fun deleteNote(note: ModelNote)

    @Query("DELETE FROM ModelNote where id = :id")
    suspend fun deleteById(id: Int?)
}