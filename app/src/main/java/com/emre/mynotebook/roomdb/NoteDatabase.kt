package com.emre.mynotebook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emre.mynotebook.model.Notes

@Database(entities = [Notes::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}