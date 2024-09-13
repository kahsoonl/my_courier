package org.example

import org.example.di.mainModule
import org.example.main.courierApp
import org.example.main.viewmodel.CourierViewModel
import org.example.main.viewmodel.CourierViewState
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    startKoin {
        modules(mainModule)
    }

    val viewModel : CourierViewModel by inject(CourierViewModel::class.java)

    while (viewModel.currentState.courierViewState != CourierViewState.ExitState) {
        courierApp(
            viewModel = viewModel,
            viewState = viewModel.currentState.courierViewState,
        )
    }
}