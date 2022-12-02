package com.example.wheeloffortune.ui.theme.ui
/**
 *  Project:"Wheel of Fortune" made by Christiaan Vink s215832/cvin
 *
 *  I want to state that I have used the code lab:
 *  https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#0
 *  Therefore it could be possible that there is code which looks similar(or is the same) to this.
 *
 */


import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.wheeloffortune.data.*
import kotlinx.coroutines.flow.*


private val _uiState = MutableStateFlow(GameUiState())
private lateinit var currentWord: String
private lateinit var hiddenWord: String
private lateinit var uiwheelstring: String
private var wheelturn: Int = 0
private var points: Int = 0

private lateinit var chosen_word: categoryandword
private lateinit var chosen_category: String



// Set of words used in the game
private var usedWords: MutableSet<String> = mutableSetOf()
private var usedLetters: MutableSet<String> = mutableSetOf()

class GameViewModel : ViewModel() {

    var userGuess by mutableStateOf("")
        private set

    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun checkUserGuess() {
        println(userGuess)

        var index = 0
        var point_multiplier = 0
        var mistake = false
        var messageisdone = false

        if (userGuess.length == 1){
            if(!usedLetters.contains(userGuess)) {
                usedLetters.add(userGuess)
                _uiState.update { currentState ->
                    currentState.copy(avaliable_letters = _uiState.value.avaliable_letters+ " " + userGuess)
                }


                if (currentWord.contains(userGuess, ignoreCase = true)) {

                    for (ch in currentWord.iterator()) {
                        val char = ch.toString()
                        println(ch)
                        if (char.equals(userGuess, ignoreCase = true)) {
                            println("celebrate")
                            point_multiplier++

                            val chars = hiddenWord.toCharArray()
                            chars[index] = ch
                            hiddenWord = String(chars)

                            _uiState.update { currentState ->
                                currentState.copy(currentScrambledWord = hiddenWord)
                            }

                            //check if the word is completed
                            if (!hiddenWord.contains("_", ignoreCase = true)) {
                                // TODO: YOU WIN

                                println("gooooodjobbb")
                                _uiState.update { currentState ->
                                    currentState.copy(isGameWon = true)
                                }
                            }


                        }
                        index++
                        println(currentWord)

                    }
                    _uiState.update { currentState ->
                        currentState.copy(isGuessedWordWrong = false)
                    }
                } else {

                    if(!messageisdone) {
                        println("you quessed wrong and lost a life")
                        _uiState.update { currentState ->
                            currentState.copy(statusmessage = "You guessed wrong and lost a life, spin the wheel.")
                        }
                        messageisdone = true
                    }
                    var lifecount = _uiState.value.lifes
                    println(lifecount)
                    lifecount--
                    println(lifecount)

                    _uiState.update { currentState ->
                        currentState.copy(lifes = lifecount)
                    }
                    if (lifecount == 0) {
                        // TODO: YOU LOST
                        println("YOU LOST")
                        _uiState.update { currentState ->
                            currentState.copy(isGameOver = true)
                        }
                    }

                }
            }else {

                if(!messageisdone) {
                    println("letter already used")
                    _uiState.update { currentState ->
                        currentState.copy(statusmessage = "Letter already used, guess again")
                    }
                    messageisdone = true
                }

                mistake = true
                _uiState.update { currentState ->
                    currentState.copy(isGuessedWordWrong = true)
                }
            }
        } else {
            if(!messageisdone) {
                println("input is not 1 letter")
                _uiState.update { currentState ->
                    currentState.copy(statusmessage = "Input is not 1 letter, guess again")
                }
                messageisdone = true
            }

            mistake = true
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        //TODO: pointmultiplier

        if (wheelturn == 0){


            if(!messageisdone) {
                println("You lost all your points")
                _uiState.update { currentState ->
                    currentState.copy(statusmessage = "You LOST all your DKK, spin the wheel.")
                }
                messageisdone = true
            }
            mistake = true
            _uiState.update { currentState ->
                currentState.copy(score = _uiState.value.score*0)
            }
        }else {
            points = wheelturn * point_multiplier

            if(!messageisdone) {
                println("you gained " + points + " points!")
                _uiState.update { currentState ->
                    currentState.copy(statusmessage = "You gained " + points + " points! Spin again!")
                }
                messageisdone = true
            }

            _uiState.update { currentState ->
                currentState.copy(score = _uiState.value.score + points)
            }
        }


        updateUserGuess("")
        //TODO: wheel

        if(!mistake){
            val wheelboolean = _uiState.value.show_wheel
            _uiState.update { currentState ->
                currentState.copy(show_wheel = !wheelboolean)
            }
        }
    }

    private fun pickRandomWordAndShuffle(): String {
        chosen_word = WordANDCategories.random()
        currentWord = chosen_word.word

        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)

            var lowercase = "_"
            var emptyword = "."
            var lenghtword = currentWord.length

            while(lenghtword != 0){
                emptyword = emptyword.plus(lowercase)
                lenghtword--
            }
            hiddenWord = emptyword.replace(".","")

            chosen_category = chosen_word.category

            _uiState.update { currentState ->
                currentState.copy(category = chosen_category)
            }
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


    fun spinthewheel(){

        wheelturn = Wheel.random()
        println(wheelturn)

        if(wheelturn!=0) {
            var wheelboolean = _uiState.value.show_wheel
            _uiState.update { currentState ->
                currentState.copy(show_wheel = !wheelboolean)
            }
        }

        println("You are spinning the wheel!")
        _uiState.update { currentState ->
            currentState.copy(statusmessage = "Guess a Letter!")
        }
        _uiState.update { currentState ->
            currentState.copy(isGuessedWordWrong = false)
        }

        if (wheelturn == 0){
            uiwheelstring = "BANKRUPT!"

            println("You lost all your points")
            _uiState.update { currentState ->
                currentState.copy(statusmessage = "You LOST all your points, spin again.")
            }
            _uiState.update { currentState ->
                currentState.copy(score = _uiState.value.score * 0)
            }
        } else {
            uiwheelstring = wheelturn.toString()
        }
        _uiState.update { currentState ->
            currentState.copy(wheel_turn = uiwheelstring)
        }


    }

//    private fun updateGameState(updatedScore: Int) {
//        if (usedWords.size == LIFES){
//            _uiState.update { currentState ->
//                currentState.copy(
//                    isGuessedWordWrong = false,
//                    isGuessedtoomanyletters = false,
//                    score = updatedScore,
//                    category = currentState.category,
//                    isGameOver = true
//                )
//            }
//        } else {
//            _uiState.update { currentState ->
//                currentState.copy(
//                    isGuessedWordWrong = false,
//                    isGuessedtoomanyletters = false,
//                    category = currentState.category,
//                    currentScrambledWord = pickRandomWordAndShuffle(),
//                    score = updatedScore
//                )
//            }
//        }
//    }


    fun resetGame() {
        //usedWords.clear()
        usedLetters.clear()
        _uiState.value = GameUiState(
            currentScrambledWord = pickRandomWordAndShuffle(),
            category = chosen_category)
    }

    init {
        resetGame()
    }
}

