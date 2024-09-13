package org.example.main.viewmodel

import org.example.common.UserIntent
import org.example.common.ViewState
import org.example.data.model.OrderModel
import org.example.data.model.UserInputType

data class ViewState (
    val courierViewState : CourierViewState
) : ViewState

sealed class CourierViewState {
    data object InitialState : CourierViewState()
    data class ErrorState(
        val errorMessage: String,
        val inputType: UserInputType,
    ) : CourierViewState()
    data object PackageInfoState : CourierViewState()
    data object FleetInfoState : CourierViewState()
    data class PrintResult(val orderModel: OrderModel) : CourierViewState()
    data object ExitState : CourierViewState()
}

sealed class CourierUserIntent : UserIntent {
    data class UserInputCostAndNoOfPackage(val lineEntry: String): CourierUserIntent()
    data class UserInputPackageInfo(val lineEntry: String): CourierUserIntent()
    data class UserInputFleetInfo(val fleetInfo: String): CourierUserIntent()
    data object ShowFleetEntry : CourierUserIntent()
    data object ProgramEnd : CourierUserIntent()
}