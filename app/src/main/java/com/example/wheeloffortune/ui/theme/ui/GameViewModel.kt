package com.example.wheeloffortune.ui.theme.ui
import com.example.wheeloffortune.data.Catergories


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.wheeloffortune.data.LIFES
import kotlinx.coroutines.flow.update


private val _uiState = MutableStateFlow(GameUiState())
private lateinit var currentWord: String
private lateinit var hiddenWord: String

// Set of words used in the game
private var usedWords: MutableSet<String> = mutableSetOf()

class GameViewModel : ViewModel() {

    var userGuess by mutableStateOf("")
        private set

    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun checkUserGuess() {
        println(userGuess)

        var index = 0

        if (userGuess.length == 1){
            for(ch in currentWord.iterator()){
                val char = ch.toString()
                println(ch)
                if(char.equals(userGuess, ignoreCase = true)){
                    println("celebrate")

                    val chars = hiddenWord.toCharArray()
                    chars[index] = ch
                    hiddenWord = String(chars)

                    _uiState.update { currentState ->
                        currentState.copy(currentScrambledWord = hiddenWord)
                    }

                }
                index++
                println(currentWord)
            }
        } else {
            println("input is wrong")
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        updateUserGuess("")





//            if (userGuess.equals(currentWord, ignoreCase = true)  ) {
//            if (userGuess.length != 1){
//                _uiState.update { currentState ->
//                    currentState.copy(isGuessedtoomanyletters = true)
//                    updateUserGuess("")
//                    return
//                }
//            } else {
//                println("goodjob")
//                val updatedScore = _uiState.value.score.plus(10)
//                updateGameState(updatedScore)
//            }
//        } else {
//            println("we should be here")
//            _uiState.update { currentState ->
//                currentState.copy(isGuessedWordWrong = true)
//            }
//        }
//        // Reset user guess
//        updateUserGuess("")
    }

    private fun pickRandomWordAndShuffle(): String {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = Catergories.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
           // return shuffleCurrentWord(currentWord)

            var lowercase = "_"
            var emptyword = "."
            var lenghtword = currentWord.length
            println(lenghtword)
            println(lowercase)

            while(lenghtword != 0){
                emptyword = emptyword.plus(lowercase)
                lenghtword--
            }
            hiddenWord = emptyword.replace(".","")
           // println(emptyword)
           // hiddenWord=emptyword
            return hiddenWord
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == LIFES){
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    isGuessedtoomanyletters = false,
                    score = updatedScore,
                    category = currentState.category,
                    isGameOver = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    isGuessedtoomanyletters = false,
                    category = currentState.category,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    score = updatedScore
                )
            }
        }
    }


    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    init {
        resetGame()
    }
}

