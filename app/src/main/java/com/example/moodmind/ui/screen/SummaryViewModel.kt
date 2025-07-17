package com.example.moodmind.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.moodmind.ui.navigation.SummaryScreenRoute
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SummaryViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var allEntries by mutableStateOf(0)
    var sadEntries by mutableStateOf(0)
    var happyEntries by mutableStateOf(0)
    var angryEntries by mutableStateOf(0)
    var surprisedEntries by mutableStateOf(0)
    var disgustedEntries by mutableStateOf(0)
    var motivatedEntries by mutableStateOf(0)
    var unmotivatedEntries by mutableStateOf(0)
    var apatheticEntries by mutableStateOf(0)
    var stressedEntries by mutableStateOf(0)
    var creativeEntries by mutableStateOf(0)
    var productiveEntries by mutableStateOf(0)
    var unproductiveEntries by mutableStateOf(0)

    private val _aiSummary = MutableStateFlow<String>("")
    val aiSummary = _aiSummary.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = "AIzaSyDq6ppwq4Kk8HlNuV2QDcOfK3sKxsGJpLA",
        generationConfig = generationConfig {
            temperature = 0.7f
            topP = 0.9f
            maxOutputTokens = 1024
        },
        safetySettings = listOf(
            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE)
        )
    )

    init {
        val route = savedStateHandle.toRoute<SummaryScreenRoute>()
        allEntries = route.allEntries
        sadEntries = route.sadEntries
        happyEntries = route.happyEntries
        angryEntries = route.angryEntries
        surprisedEntries = route.surprisedEntries
        disgustedEntries = route.disgustedEntries
        motivatedEntries = route.motivatedEntries
        unmotivatedEntries = route.unmotivatedEntries
        apatheticEntries = route.apatheticEntries
        stressedEntries = route.stressedEntries
        creativeEntries = route.creativeEntries
        productiveEntries = route.productiveEntries
        unproductiveEntries = route.unproductiveEntries

        generateAISummary()
    }
    // unable to extract strings in this function since function overall exists in the
    // view model with no @Composable tag (throws error)
    fun generateAISummary() {
        _aiSummary.value = "Analyzing your entries..."

        val moodMap = mapOf(
            "Sad" to sadEntries,
            "Happy" to happyEntries,
            "Angry" to angryEntries,
            "Surprised" to surprisedEntries,
            "Disgusted" to disgustedEntries,
            "Motivated" to motivatedEntries,
            "Unmotivated" to unmotivatedEntries,
            "Apathetic" to apatheticEntries,
            "Stressed" to stressedEntries,
            "Creative" to creativeEntries,
            "Productive" to productiveEntries,
            "Unproductive" to unproductiveEntries
        )

        val moodSummary = moodMap.entries.joinToString(", ") { "${it.key}: ${it.value}" }

        val prompt = """
            The user has logged the following mood entries: $moodSummary.
            Based on this, give a short (3–4 sentence) summary of the user's emotional well-being and suggest one piece of advice.
            Talk directly to the user as if you were a therapist.
        """.trimIndent()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = generativeModel.generateContent(prompt)
                _aiSummary.value = result.text ?: "No response from AI."
            } catch (e: Exception) {
                _aiSummary.value = "Error generating summary: ${e.localizedMessage}"
            }
        }
    }
}
