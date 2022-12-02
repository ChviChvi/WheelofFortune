package com.example.wheeloffortune.ui.theme.ui
/**
 *  Project:"Wheel of Fortune" made by Christiaan Vink s215832/cvin
 *
 *  I want to state that I have used the code lab:
 *  https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#0
 *  Therefore it could be possible that there is code which looks similar(or is the same) to this.
 *
 */


data class GameUiState(
    val currentScrambledWord: String = "",
    val hiddenScrambledWord: String = "",
    val isGuessedWordWrong: Boolean = false,
    val isGuessedtoomanyletters: Boolean = false,
    val score: Int = 0,

    val statusmessage: String = "Spin the wheel!",

    val category:  String = "",

    val lifes: Int = 5,
    val isGameOver: Boolean = false,
    val isGameWon: Boolean = false,

    val show_wheel: Boolean = true,
    val wheel_turn: String ="",

    //val wrong_letters: String = "",
    // val correct_letters: String = "",
    val avaliable_letters: String = " "
)
