package com.emre.mynotebook.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emre.mynotebook.model.Notes
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface NoteDao {

    @Query("select * from Notes")
    fun getAll(): Flowable<List<Notes>>

    @Insert
    fun insert(note: Notes): Completable

    @Delete
    fun delete(note: Notes): Completable

    @Update
    fun update(note: Notes): Completable
}