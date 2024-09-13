package org.example.main.view

import org.example.main.viewmodel.CourierUserIntent
import java.util.*

fun FleetView(
    userIntent: (CourierUserIntent) -> Unit,
) {
    println("Enter number of car speed and maximum carry limit")
    val reader = Scanner(System.`in`)
    val fleetInfo = reader.nextLine()
    userIntent.invoke(
        CourierUserIntent.UserInputFleetInfo(fleetInfo = fleetInfo)
    )
}