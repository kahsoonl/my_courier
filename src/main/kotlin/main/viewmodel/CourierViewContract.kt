package org.example.main.viewmodel

import org.example.common.UserIntent
import org.example.common.ViewState
import org.example.common.emptyValue
import org.example.data.model.OfferModel
import org.example.data.model.OrderModel
import org.example.data.model.UserInputType

data class ViewState (
    val courierViewState : CourierViewState
) : ViewState

sealed class CourierViewState {
    data object InitialState : CourierViewState()
    data class ManagePromoState(
        val offerList: List<OfferModel>
    ) : CourierViewState()
    data class ErrorState(
        val errorMessage: String,
        val inputType: UserInputType,
    ) : CourierViewState()
    data object CostAndPackageState : CourierViewState()
    data object PackageInfoState : CourierViewState()
    data object FleetInfoState : CourierViewState()
    data class PrintResult(val orderModel: OrderModel) : CourierViewState()
    data object ExitState : CourierViewState()
    data class AddOffer(val message: String = String.emptyValue()) : CourierViewState()
    data class DeleteOffer(val message: String = String.emptyValue()) : CourierViewState()
}

sealed class CourierUserIntent : UserIntent {
    data class UserInputMenuSelection(val userInput: String): CourierUserIntent()
    data class UserInputCostAndNoOfPackage(val lineEntry: String): CourierUserIntent()
    data class UserInputPackageInfo(val lineEntry: String): CourierUserIntent()
    data class UserInputFleetInfo(val fleetInfo: String): CourierUserIntent()
    data class UserInputOfferMenu(val offerMenuInput: String): CourierUserIntent()
    data class UserInputOfferIdDelete(val offerId: String) : CourierUserIntent()
    data class UserInputAddOffer(val offerInput: String): CourierUserIntent()
    data object ProgramEnd : CourierUserIntent()
}