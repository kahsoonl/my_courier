package org.example.main.viewmodel

import org.example.common.MviViewModel
import org.example.data.model.OfferModel
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
            is CourierUserIntent.UserInputMenuSelection -> {
                readMenuSelection(intent.userInput)
            }

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

            is CourierUserIntent.UserInputOfferMenu -> {
                readOfferMenuInput(intent.offerMenuInput)
            }

            is CourierUserIntent.UserInputOfferIdDelete -> {
                readDeleteOfferIdInput(intent.offerId)
            }

            is CourierUserIntent.UserInputAddOffer -> {
                readAddOfferInput(intent.offerInput)
            }
        }
    }

    private fun readMenuSelection(userInput: String) {
        val isMenuInputValid = userInput.trim().all { it.isDigit() }

        if (isMenuInputValid) {
            when (userInput.toInt()) {
                1 -> {
                    updateState(
                        state = ViewState(
                            courierViewState = CourierViewState.CostAndPackageState,
                        )
                    )
                }

                2 -> {
                    getOfferAndUpdate()
                }

                3 -> {
                    updateState(
                        state = ViewState(
                            courierViewState = CourierViewState.ExitState,
                        )
                    )
                }
            }
        } else {
            showError(
                errorMessage = "Invalid menu selection, please enter a valid menu selection",
                inputType = UserInputType.MENU_SELECTION,
            )
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
            showError(
                errorMessage = "Invalid input format, please enter only digits and follows the format \nbase_delivery_cost no_of_packages",
                inputType = UserInputType.COST_INFO,
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
            packageInputCounter++

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
            }
        } else {
            showError(
                errorMessage = "Invalid input format, please follows the format and enter unique package id for each package \npkg_id pkg_weight_kg pkg_distance_km offer_code",
                inputType = UserInputType.PACKAGE_INFO,
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
            showError(
                errorMessage = "Invalid input format, please enter only digits and follows the format \n" +
                        "no_of_vehicles max_speed max_carriable_weight",
                inputType = UserInputType.FLEET_INFO,
            )
        }
    }

    private fun showError(errorMessage: String, inputType: UserInputType) {
        updateState(
            state = ViewState(
                courierViewState = CourierViewState.ErrorState(
                    errorMessage = errorMessage,
                    inputType = inputType,
                )
            )
        )
    }

    private fun getOfferAndUpdate() {
        val offerList = courierUseCase.getOfferFromLocal()

        updateState(
            state = ViewState(
                courierViewState = CourierViewState.ManagePromoState(offerList = offerList),
            )
        )
    }

    private fun readOfferMenuInput(offerMenuInput: String) {
        val isOfferMenuInputValid = offerMenuInput.trim().all { it.isDigit() }

        if (isOfferMenuInputValid) {
            when (offerMenuInput.toInt()) {
                1 -> {
                    updateState(
                        state = ViewState(
                            courierViewState = CourierViewState.AddOffer(),
                        )
                    )
                }

                2 -> {
                    updateState(
                        state = ViewState(
                            courierViewState = CourierViewState.DeleteOffer(),
                        )
                    )
                }

                3 -> {
                    updateState(
                        state = ViewState(
                            courierViewState = CourierViewState.InitialState,
                        )
                    )
                }
            }
        } else {
            showError(
                errorMessage = "Invalid menu selection, please enter a valid menu selection",
                inputType = UserInputType.OFFER_MENU_SELECTION,
            )
        }
    }

    private fun readAddOfferInput(offerInfoInput: String) {
        val offerInput = offerInfoInput.split(" ")
        val currentOffers = courierUseCase.getOfferFromLocal()
        val isValidOfferInput = offerInput.size == 6 &&
                currentOffers.none { it.offerId == offerInput[0] } &&
                offerInput[1].all { it.isDigit() } &&
                offerInput[2].all { it.isDigit() } &&
                offerInput[3].all { it.isDigit() } &&
                offerInput[4].all { it.isDigit() } &&
                offerInput[5].all { it.isDigit() } &&
                offerInput[1].toInt() < offerInput[2].toInt() &&
                offerInput[3].toInt() < offerInput[4].toInt()

        if(isValidOfferInput) {
            val parsedOffer = OfferModel(
                offerId = offerInput[0],
                offerMinDistance = offerInput[1].toInt(),
                offerMaxDistance = offerInput[2].toInt(),
                offerMinWeight = offerInput[3].toInt(),
                offerMaxWeight = offerInput[4].toInt(),
                offerDiscountPercentage = offerInput[5].toInt(),
            )
            val insertResult = courierUseCase.addOfferToLocal(parsedOffer)

            if(insertResult) {
                updateState(
                    state = ViewState(
                        courierViewState = CourierViewState.AddOffer(message = "Offer successfully inserted")
                    )
                )
            } else {
                showError(
                    errorMessage = "Something went wrong while inserting, please ensure that your input follows the format and minimum value does not exceed maximum value\npkg_id pkg_min_distance pkg_max_distance pkg_min_weight pkg_max_weight pkg_disc_percentage",
                    inputType = UserInputType.OFFER_INFO,
                )
            }
        } else {
            showError(
                errorMessage = "Invalid input, please ensure that your input follows the format and minimum value does not exceed maximum value\npkg_id pkg_min_distance pkg_max_distance pkg_min_weight pkg_max_weight pkg_disc_percentage",
                inputType = UserInputType.OFFER_INFO,
            )
        }
    }

    private fun readDeleteOfferIdInput(offerIdInput: String) {
        val offerId = offerIdInput.trim()
        val isDeleted = courierUseCase.deleteOffer(offerId)

        if(isDeleted) {
            updateState(
                state = ViewState(
                    courierViewState = CourierViewState.DeleteOffer("Offer removed")
                )
            )
        } else {
            updateState(
                state = ViewState(
                    courierViewState = CourierViewState.DeleteOffer("No record is found with provided offer id: '$offerIdInput'")
                )
            )
        }
    }
}