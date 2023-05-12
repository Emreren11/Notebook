package com.emre.mynotebook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import java.io.Serializable

@Entity
class Notes(

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "mainText")
    var mainText: String
    ) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}