package com.example.moodmind.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDAO {
    @Query("SELECT * FROM moodtable")
    fun getAllEntries() : Flow<List<MoodItem>>

    @Query("SELECT * from moodtable WHERE id = :id")
    fun getEntry(id: Int): Flow<MoodItem>

    @Query("SELECT COUNT(*) from moodtable")
    suspend fun getEntriesNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Sad"""")
    suspend fun getSadItemsNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Happy"""")
    suspend fun getHappyItemsNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Angry"""")
    suspend fun getAngryItemsNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Surprised"""")
    suspend fun getSurprisedItemsNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Disgusted"""")
    suspend fun getDisgustedItemsNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Motivated"""")
    suspend fun getMotivatedItemsNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Unmotivated"""")
    suspend fun getUnmotivatedItemsNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Apathetic"""")
    suspend fun getApatheticItemsNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Stressed"""")
    suspend fun getStressedItemsNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Creative"""")
    suspend fun getCreativeItemsNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Productive"""")
    suspend fun getProductiveItemsNum(): Int

    @Query("""SELECT COUNT(*) from moodtable WHERE category="Unproductive"""")
    suspend fun getUnproductiveItemsNum(): Int


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: MoodItem)

    @Update
    suspend fun update(item: MoodItem)

    @Delete
    suspend fun delete(item: MoodItem)

    @Query("DELETE from moodtable")
    suspend fun deleteAllEntries()


}