package com.example.wheeloffortune.ui.theme.ui

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
            score = gameUiState.score
        )
        GuessingWord(currentScrambledWord = gameUiState.currentScrambledWord)
        LetterOptions(
            currentScrambledWord = gameUiState.currentScrambledWord,
            userGuess = gameViewModel.userGuess,
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            onKeyboardDone = { gameViewModel.checkUserGuess() },
            isGuessWrong =  gameUiState.isGuessedWordWrong,
            isGuesstooLong = gameUiState.isGuessedtoomanyletters
        )
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(top = 0.dp, start = 30.dp, end = 30.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp),
                onClick = { gameViewModel.checkUserGuess()
                }
            ) {
                Text("try me")
            }
        }

        Text(text = "Hello Savanna!" , modifier = Modifier.clickable {

        })
        //Letter(currentScrambledWord = gameUiState.currentScrambledWord)
    }


}

@Composable
fun Letter(currentScrambledWord: String,
           modifier: Modifier = Modifier,){
    val myList: SnapshotStateList<String> = remember {
        mutableStateListOf()
    }
    val color = remember {
        mutableStateOf(Color.Green)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        var selected by remember { mutableStateOf(false) }
        val color = if (selected) Color.Gray else Color.Yellow



        Button(
            onClick = { selected  = !selected

                      },
            colors = ButtonDefaults.buttonColors(backgroundColor = color),
            modifier = Modifier
                .height(10.dp)
                .width(20.dp),
            enabled = true
        ){
            Text("B")
        }
        //here
        
//        Box(modifier = modifier
//            .background(color.value)
//            .clickable {
//                color.value = Color(
//                    Random.nextFloat(),
//                    Random.nextFloat(),
//                    Random.nextFloat(),
//                    1f
//                )
//            }
//        )

    }
    
}

@Composable
fun CategoryandPoint(Lifes: Int, score: Int,modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp),
    ) {
        Text(
            text = stringResource(R.string.lifes, Lifes),
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
                     isGuesstooLong : Boolean){

    var selected by remember { mutableStateOf(false) }
    var color =  if(selected) Color.White else Color.Blue

    val maxLenght = 1
    val maxContext = LocalContext.current //https://www.geeksforgeeks.org/how-to-set-maximum-input-length-in-textfield-in-android-using-jetpack-compose/

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
                        imeAction = ImeAction.Done
                    ),
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
                    text = "Wrong input",
                    color = Color.Red,
                    fontSize = 18.sp,
                )
            }
            if(isGuesstooLong){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start),
                    text = "Max 1 Character",
                    color = Color.Red,
                    fontSize = 18.sp,
                )
            }

    }


            // FIRST ROW OF LETTERS
            Row(modifier = modifier
                .fillMaxWidth()
                .padding(top = 0.dp, start = 30.dp, end = 30.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        //.height(20.dp)
                        //.width(10.dp)
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        if(!selected) {
                            selected = !selected

                        }

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = color),

                    ) {
                    //add this to make the buttons go to the middle.
                    Text(text =stringResource(R.string.Alphabet_Q),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )

                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { println("turn me off please") }
                ) {
                    Text(stringResource(R.string.Alphabet_W))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_E))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_R))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_T))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_Y))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_U))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_I))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = {  }
                ) {
                    Text(stringResource(R.string.Alphabet_O))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),

                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_P))
                }

            }
            //
            //SECOND ROW OF LETTERS
            //
            Row(modifier = modifier
                .fillMaxWidth()
                .padding(top = 0.dp, start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_A))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_S))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_D))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_F))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_G))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_H))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_J))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_K))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_L))
                }

            }
            //
            //THIRD ROW OF LETTERS
            //
            Row(modifier = modifier
                .fillMaxWidth()
                .padding(top = 0.dp, start = 70.dp, end = 70.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_Z))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_X))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_C))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_V))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_B))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_N))
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 8.dp),
                    contentPadding = PaddingValues(0.dp),
                    onClick = { }
                ) {
                    Text(stringResource(R.string.Alphabet_M))
                }

            }
        }
    }


@Composable
fun keyboardbutton(string: Int, modifier: Modifier ){
    Row() {
        Button(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 8.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = { }
        ) {
            Text(stringResource(string))
        }
    }
}

private fun getData(): List<String> {
    val myList = mutableListOf<String>()
    repeat(20) {
        myList.add("Row $it")
    }

    return myList.toList()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        Screen()
    }

