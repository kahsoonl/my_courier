package org.example.main.view

import org.example.data.model.UserInputType
import org.example.main.viewmodel.CourierUserIntent
import org.example.main.viewmodel.CourierViewState
import java.util.*

fun ErrorView(
    viewState: CourierViewState.ErrorState,
    userIntent: (CourierUserIntent) -> Unit,
) {
    println(viewState.errorMessage)

    val reader = Scanner(System.`in`)
    val userInput = reader.nextLine()

    when (viewState.inputType) {
        UserInputType.PACKAGE_INFO -> {
            userIntent.invoke(
                CourierUserIntent.UserInputPackageInfo(userInput)
            )
        }
        UserInputType.COST_INFO -> {
            userIntent.invoke(
                CourierUserIntent.UserInputCostAndNoOfPackage(userInput)
            )
        }
        UserInputType.FLEET_INFO -> {
            userIntent.invoke(
                CourierUserIntent.UserInputFleetInfo(userInput)
            )
        }
    }
}