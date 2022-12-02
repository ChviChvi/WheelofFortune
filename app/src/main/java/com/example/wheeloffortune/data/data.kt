package com.example.wheeloffortune.data
/**
 *  Project:"Wheel of Fortune" made by Christiaan Vink s215832/cvin
 *
 *  I want to state that I have used the code lab as it was very helpful:
 *  https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#0
 *  Therefore it could be possible that there is code which looks similar(or is the same) to this.
 *
 */
const val LIFES = 5

    val WordANDCategories: List<categoryandword> =
        listOf(
            categoryandword("Pancake","Food"),
            categoryandword("Muffin","Food"),
            categoryandword("Chocolate","Food"),
            categoryandword("Bread","Food"),
            categoryandword("Pancake","Food"),
            categoryandword("Rhino","Animal"),
            categoryandword("Hedgehog","Animal"),
            categoryandword("Dog","Animal"),
            categoryandword("Monkey","Animal"),
            categoryandword("Alligator","Animal"),
            categoryandword("Crocodile","Animal"),
            categoryandword("Peanutbutter","Food"),
            categoryandword("Koala","Animal"),
            categoryandword("Monkey","Animal"),
            categoryandword("Netherlands","Country"),
            categoryandword("Denmark","Country"),
            categoryandword("Germany","Country"),
            categoryandword("Sweden","Country"),
            categoryandword("Norway","Country"),
            categoryandword("Finland","Country"),
            categoryandword("France","Country"),


        )


    val Wheel: Set<Int> =
        setOf(
            250,
            500,
            0,
            1000,
            1500,
            2000,
            3000,
            600,
            700,
            800,
            900,
            600,
            700,
            800,
            900,
            250,
            500,
            250,
            500,

    )

