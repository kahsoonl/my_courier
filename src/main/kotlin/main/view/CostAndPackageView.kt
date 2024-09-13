package org.example.main.view

import org.example.main.viewmodel.CourierUserIntent
import java.util.*

fun CostAndPackageView(
    userIntent: (CourierUserIntent) -> Unit,
) {
    println("Enter base delivery cost and number of packages:")
    val reader = Scanner(System.`in`)
    val costAndNumber = reader.nextLine()
    userIntent.invoke(
        CourierUserIntent.UserInputCostAndNoOfPackage(lineEntry = costAndNumber)
    )
}