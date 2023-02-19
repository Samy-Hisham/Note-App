package com.android.noteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.noteapp.data.models.ModelNote


@Database(entities = [ModelNote::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getDao() : Dao
}