package com.example.wheeloffortune.ui.theme.ui
/**
 *  Project:"Wheel of Fortune" made by Christiaan Vink s215832/cvin
 *
 *  I want to state that I have used the code lab as it was very helpful:
 *  https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#0
 *  Therefore it could be possible that there is code which looks similar(or is the same) to this.
 *
 */
import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wheeloffortune.R


@Composable
fun Screen (modifier: Modifier = Modifier,
            gameViewModel: GameViewModel= viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()


    Column(horizontalAlignment = Alignment.CenterHorizontally) {



        CategoryandPoint(
            Lifes = gameUiState.lifes,
            score = gameUiState.score,
            category = gameUiState.category
        )

        println(gameUiState.category + " bye")
        GuessingWord(currentScrambledWord = gameUiState.currentScrambledWord)

        LetterOptions(
            currentScrambledWord = gameUiState.currentScrambledWord,
            userGuess = gameViewModel.userGuess,
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            onKeyboardDone = { gameViewModel.checkUserGuess() },
            isGuessWrong =  gameUiState.isGuessedWordWrong,
            isGuesstooLong = gameUiState.isGuessedtoomanyletters,
            errormessage = gameUiState.statusmessage,
        )
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(top = 0.dp, start = 30.dp, end = 30.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if(gameUiState.show_wheel) {
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    onClick = {
                        gameViewModel.spinthewheel()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF99BF72))
                        //Color(0xFF99BF72)

                ) {
                    Text("SPIN!")
                }
            } else{
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    onClick = {
                        gameViewModel.checkUserGuess()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE38F2D))

                ) {
                    Text("Quess!")
                }
            }
        }
        if(gameUiState.wheel_turn != "") {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, start = 30.dp, end = 30.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    text = "YOU SPUN " + gameUiState.wheel_turn,
                    fontSize = 18.sp,
                )
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 0.dp, start = 30.dp, end = 30.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.Start),
                text = "Guessed Letters:"+ gameUiState.avaliable_letters ,
                fontSize = 18.sp,
            )
        }



    }
    if (gameUiState.isGameOver) {
        FinalScoreDialog(
            score = gameUiState.score,
            isGameOver = gameUiState.isGameOver,
            isGameWon = gameUiState.isGameWon,
            onPlayAgain = { gameViewModel.resetGame() }
        )
    }
    if(gameUiState.isGameWon){
        FinalScoreDialog(
            score = gameUiState.score,
            isGameOver = gameUiState.isGameOver,
            isGameWon = gameUiState.isGameWon,
            onPlayAgain = { gameViewModel.resetGame() }
        )
    }

}

@Composable
fun CategoryandPoint(
    Lifes: Int,
    score: Int,
    modifier: Modifier = Modifier,
    category: String,
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .size(38.dp),
    ) {
        Text(

            fontSize = 28.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),

            text = stringResource(R.string.category,category),
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp),
    ) {
        Text(
            text = stringResource(R.string.lives, Lifes),
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(R.string.score, score),
            fontSize = 18.sp,
        )
    }
}

@Composable
fun GuessingWord(currentScrambledWord: String,
                 modifier: Modifier = Modifier,){
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = currentScrambledWord,
            letterSpacing = 4.sp,
            fontSize = 45.sp,

            modifier = modifier.align(Alignment.CenterHorizontally)
        )
    }
}
var count = 0
@Composable
fun LetterOptions(   currentScrambledWord: String,
                     userGuess: String,
                     isGuessWrong: Boolean,
                     onUserGuessChanged: (String) -> Unit,
                     onKeyboardDone: () -> Unit,
                     modifier: Modifier = Modifier,
                     errormessage: String,
                     isGuesstooLong : Boolean){

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
            Row(modifier = modifier
                .fillMaxWidth()
                .padding(top = 0.dp, start = 30.dp, end = 30.dp),
            horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedTextField(
                    value = userGuess,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = onUserGuessChanged,
                    label = { Text("enter a letter") },
                    isError = isGuessWrong || isGuesstooLong,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { onKeyboardDone() }
                    ),


                )
            }
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(top = 0.dp, start = 30.dp, end = 30.dp),
        horizontalArrangement = Arrangement.Center
        ) {

            if(isGuessWrong){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start),
                    text = errormessage,
                    color = colorResource(R.color.error_color),
                    fontSize = 15.sp,
                )
            } else {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start),
                    text = errormessage,
                    //color = col,
                    fontSize = 15.sp,
                )
            }
    }
        }
    }

@Composable
private fun FinalScoreDialog(
    onPlayAgain: () -> Unit,
    score: Int,
    isGameOver: Boolean,
    isGameWon: Boolean,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {},
        title = {
                    if(isGameWon){
                        Text(stringResource(R.string.win))
                    }
                    if(isGameOver){
                        Text(stringResource(R.string.lose))
                    }
                },
        text = { Text(stringResource(R.string.finalscore, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exitgame))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onPlayAgain()
                }
            ) {
                Text(text = stringResource(R.string.playagain))
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        Screen()
    }

