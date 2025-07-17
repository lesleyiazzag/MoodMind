package com.example.moodmind.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object MoodMindScreenRoute

@Serializable
data class SummaryScreenRoute(
    val allEntries: Int,
    val sadEntries: Int,
    val happyEntries: Int,
    val angryEntries: Int,
    val surprisedEntries: Int,
    val disgustedEntries: Int,
    val motivatedEntries: Int,
    val unmotivatedEntries: Int,
    val apatheticEntries: Int,
    val stressedEntries: Int,
    val creativeEntries: Int,
    val productiveEntries: Int,
    val unproductiveEntries: Int
)

@Serializable
object SplashScreenRoute
