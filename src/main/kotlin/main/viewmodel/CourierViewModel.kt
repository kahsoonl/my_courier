package org.example.main.viewmodel

import org.example.common.MviViewModel
import org.example.data.model.OrderModel
import org.example.data.model.PackageModel
import org.example.data.model.UserInputType
import org.example.domain.CourierUseCase

class CourierViewModel(
    private val courierUseCase: CourierUseCase,
) : MviViewModel<CourierUserIntent, ViewState>() {
    private var currentOrder: OrderModel = OrderModel.emptyValue
    private var packageInputCounter = 0

    init {
        updateState(
            state = ViewState(
                courierViewState = CourierViewState.InitialState
            )
        )
    }

    override fun handleIntent(intent: CourierUserIntent) {
        when (intent) {
            is CourierUserIntent.UserInputCostAndNoOfPackage -> {
                getBaseCostAndNumberOfPackage(intent.lineEntry)
            }

            is CourierUserIntent.UserInputPackageInfo -> {
                getPackageInfo(intent.lineEntry)
            }

            is CourierUserIntent.ProgramEnd -> {
                updateState(
                    ViewState(
                        courierViewState = CourierViewState.ExitState
                    )
                )
            }

            is CourierUserIntent.UserInputFleetInfo -> {
                setFleetInfoAndShowResult(intent.fleetInfo)
            }

            is CourierUserIntent.ShowFleetEntry -> {
                updateState(
                    ViewState(
                        courierViewState = CourierViewState.FleetInfoState
                    )
                )
            }
        }
    }

    private fun getBaseCostAndNumberOfPackage(userInput: String) {
        val costAndNumberOfPackage = userInput.split(" ")
        val isValidCostInput = costAndNumberOfPackage.all { costInput -> costInput.all { it.isDigit() } }

        if (isValidCostInput) {
            packageInputCounter = 0

            currentOrder = currentOrder.copy(
                baseCost = costAndNumberOfPackage[0].toInt(),
                numberOfItem = costAndNumberOfPackage[1].toInt(),
            )

            updateState(
                state = ViewState(
                    courierViewState = CourierViewState.PackageInfoState
                )
            )
        } else {
            updateState(
                state = ViewState(
                    courierViewState = CourierViewState.ErrorState(
                        errorMessage = "Invalid input format, please enter only digits and follows the format \nbase_delivery_cost no_of_packages",
                        inputType = UserInputType.COST_INFO,
                    )
                )
            )
        }
    }

    private fun getPackageInfo(userInput: String) {
        val packageInfo = userInput.split(" ")
        val currentPackageList = currentOrder.packageList.toMutableList()
        val isValidPackageInput = packageInfo.size == 4 &&
                currentPackageList.none { it.packageId == packageInfo[0] } &&
                packageInfo[1].all { it.isDigit() } &&
                packageInfo[2].all { it.isDigit() }

        if (isValidPackageInput) {
            currentPackageList.add(
                PackageModel(
                    packageId = packageInfo[0],
                    packageWeight = packageInfo[1].toInt(),
                    packageDistance = packageInfo[2].toInt(),
                    offerCode = packageInfo[3],
                )
            )
            currentOrder = currentOrder.copy(
                packageList = currentPackageList
            )

            if (packageInputCounter == currentOrder.numberOfItem) {
                updateState(
                    state = ViewState(
                        courierViewState = CourierViewState.FleetInfoState
                    )
                )
            } else {
                updateState(
                    state = ViewState(
                        courierViewState = CourierViewState.PackageInfoState
                    )
                )
                packageInputCounter++
            }
        } else {
            updateState(
                state = ViewState(
                    courierViewState = CourierViewState.ErrorState(
                        errorMessage = "Invalid input format, please follows the format and enter unique package id for each package \npkg_id pkg_weight_kg pkg_distance_km offer_code",
                        inputType = UserInputType.PACKAGE_INFO,
                    )
                )
            )
        }
    }

    private fun setFleetInfoAndShowResult(userInput: String) {
        val fleetInfo = userInput.split(" ")
        val isValidFleetInput = fleetInfo.all { fleetValue -> fleetValue.all { it.isDigit() } }

        if (isValidFleetInput) {
            currentOrder = currentOrder.copy(
                numberOfCar = fleetInfo[0].toInt(),
                maximumSpeedLimit = fleetInfo[1].toInt(),
                maximumCarryWeight = fleetInfo[2].toInt(),
            )
            currentOrder = courierUseCase.calculateCostAndTime(currentOrder)
            updateState(
                ViewState(
                    courierViewState = CourierViewState.PrintResult(
                        orderModel = currentOrder,
                    )
                )
            )
        } else {
            updateState(
                state = ViewState(
                    courierViewState = CourierViewState.ErrorState(
                        errorMessage = "Invalid input format, please enter only digits and follows the format \nno_of_vehicles max_speed max_carriable_weight",
                        inputType = UserInputType.COST_INFO,
                    )
                )
            )
        }
    }
}