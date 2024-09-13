package org.example.main

import org.example.main.view.*
import org.example.main.viewmodel.CourierUserIntent
import org.example.main.viewmodel.CourierViewModel
import org.example.main.viewmodel.CourierViewState

fun courierApp(
    viewModel: CourierViewModel,
    viewState: CourierViewState,
) {
    fun onUserAction(): (CourierUserIntent) -> Unit = {
        viewModel.handleIntent(it)
    }

    when (viewState) {
        is CourierViewState.InitialState -> {
            CostAndPackageView(
                userIntent = onUserAction()
            )
        }
        is CourierViewState.PackageInfoState -> {
            PackageInfoView(
                userIntent = onUserAction()
            )
            //onUserAction().invoke(CourierUserIntent.ShowFleetEntry)
        }
        is CourierViewState.FleetInfoState -> {
            FleetView(
                userIntent = onUserAction()
            )
        }
        is CourierViewState.PrintResult -> {
            ResultView(
                viewState = viewState,
                userIntent = onUserAction(),
            )
        }
        is CourierViewState.ErrorState -> {
            ErrorView(
                viewState = viewState,
                userIntent = onUserAction(),
            )
        }
        is CourierViewState.ExitState -> {
            return
        }
    }
}