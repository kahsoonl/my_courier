package org.example.main.view

import org.example.main.viewmodel.CourierUserIntent
import org.example.main.viewmodel.CourierViewState

fun ResultView(
    viewState: CourierViewState.PrintResult,
    userIntent: (CourierUserIntent) -> Unit,
) {
    viewState.orderModel.packageList.forEach {
        println(it.toString())
    }
    userIntent.invoke(
        CourierUserIntent.ProgramEnd
    )
}