package com.example.wheeloffortune.ui.theme.ui

import android.media.MediaRouter.RouteCategory

data class GameUiState(
    val currentScrambledWord: String = "",
    val hiddenScrambledWord: String = "",
    val isGuessedWordWrong: Boolean = false,
    val isGuessedtoomanyletters: Boolean = false,
    val score: Int = 0,
    val category:  String = "",
    val lifes: Int = 0,
    val isGameOver: Boolean = false
)
