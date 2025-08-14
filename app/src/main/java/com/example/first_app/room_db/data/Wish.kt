package com.example.first_app.room_db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "wish-title")
    val title: String = "",

    @ColumnInfo(name = "wish-description")
    val description: String = ""
)

object DummyWish {
    val wishList = listOf(
        Wish(title = "Google", description = "Google Description"),
        Wish(title = "FaceBook", description = "Facebook Description"),
        Wish(title = "Amazon", description = "Amazon Description"),
    )
}