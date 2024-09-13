package org.example.main.view

import org.example.main.viewmodel.CourierUserIntent
import java.util.*

fun PackageInfoView(
    userIntent: (CourierUserIntent) -> Unit,
) {
    println("Enter package ID, weight, delivery distance and offer code:")
    val reader = Scanner(System.`in`)
    val costAndNumber = reader.nextLine()
    userIntent.invoke(
        CourierUserIntent.UserInputPackageInfo(lineEntry = costAndNumber)
    )
}