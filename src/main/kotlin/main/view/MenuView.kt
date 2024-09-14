package org.example.main.view

import org.example.main.viewmodel.CourierUserIntent
import java.util.*

fun MenuView(
    userIntent: (CourierUserIntent) -> Unit,
) {
    println("1. Enter a new order\n2. Check & Manage Offers\n3. Exit")
    val reader = Scanner(System.`in`)
    val menuSelection = reader.nextLine()
    userIntent.invoke(
        CourierUserIntent.UserInputMenuSelection(userInput = menuSelection)
    )
}