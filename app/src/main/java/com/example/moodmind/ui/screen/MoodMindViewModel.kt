package com.example.moodmind.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodmind.data.MoodCategory
import com.example.moodmind.data.MoodDAO
import com.example.moodmind.data.MoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoodMindViewModel @Inject constructor(val moodDAO: MoodDAO): ViewModel() {

    fun getAllEntryList(): Flow<List<MoodItem>> {
        return moodDAO.getAllEntries()
    }

    suspend fun getAllEntryNum(): Int {
        return moodDAO.getEntriesNum()
    }

    suspend fun getSadItemsNum(): Int {
        return moodDAO.getSadItemsNum()
    }

    suspend fun getHappyItemsNum(): Int {
        return moodDAO.getHappyItemsNum()
    }

    suspend fun getAngryItemsNum(): Int {
        return moodDAO.getAngryItemsNum()
    }

    suspend fun getSurprisedItemsNum(): Int {
        return moodDAO.getSurprisedItemsNum()
    }

    suspend fun getDisgustedItemsNum(): Int {
        return moodDAO.getDisgustedItemsNum()
    }

    suspend fun getMotivatedItemsNum(): Int {
        return moodDAO.getMotivatedItemsNum()
    }

    suspend fun getUnmotivatedItemsNum(): Int {
        return moodDAO.getUnmotivatedItemsNum()
    }

    suspend fun getApatheticItemsNum(): Int {
        return moodDAO.getApatheticItemsNum()
    }

    suspend fun getStressedItemsNum(): Int {
        return moodDAO.getStressedItemsNum()
    }

    suspend fun getCreativeItemsNum(): Int {
        return moodDAO.getCreativeItemsNum()
    }

    suspend fun getProductiveItemsNum(): Int {
        return moodDAO.getProductiveItemsNum()
    }

    suspend fun getUnproductiveItemsNum(): Int {
        return moodDAO.getUnproductiveItemsNum()
    }

    fun addEntryList(item: MoodItem) {
        viewModelScope.launch() {
            moodDAO.insert(item)
        }
    }

    fun removeEntryItem(item: MoodItem) {
        viewModelScope.launch {
            moodDAO.delete(item)
        }
    }

    fun editEntryItem(editedEntry: MoodItem) {
        viewModelScope.launch {
            moodDAO.update(editedEntry)
        }
    }

    fun changeEntryState(item: MoodItem) {
        val updatedEntry = item.copy()
        viewModelScope.launch {
            moodDAO.update(updatedEntry)
        }
    }

    fun clearAllEntries() {
        viewModelScope.launch {
            moodDAO.deleteAllEntries()
        }
    }


}
