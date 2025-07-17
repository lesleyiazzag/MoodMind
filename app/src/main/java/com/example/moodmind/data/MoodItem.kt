package com.example.moodmind.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moodmind.R
import java.io.Serializable
import java.time.LocalDate

@Entity(tableName = "moodtable")
data class MoodItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "createDate") val createDate:String,
    @ColumnInfo(name = "category") var category:MoodCategory,
) : Serializable

enum class MoodCategory {
    Sad, Happy, Angry, Surprised, Disgusted,
    Motivated, Unmotivated, Apathetic, Stressed,
    Creative, Productive, Unproductive;

    fun getIcon(): Int {
        return if (this == Sad)  {
            R.drawable.sad
        } else if (this == Happy) {
            R.drawable.happy
        } else if (this == Angry) {
            R.drawable.angry
        } else if (this == Surprised) {
            R.drawable.surprised
        } else if (this == Disgusted) {
            R.drawable.disgusted
        } else if (this == Motivated) {
            R.drawable.motivated
        } else if (this == Unmotivated) {
            R.drawable.unmotivated
        } else if (this == Apathetic) {
            R.drawable.apathetic
        } else if (this == Stressed) {
            R.drawable.stressed
        } else if (this == Creative) {
            R.drawable.creative
        } else if (this == Productive) {
            R.drawable.productive
        } else {
            R.drawable.unproductive
        }
    }
}
