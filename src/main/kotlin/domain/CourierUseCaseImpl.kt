package org.example.domain

import org.example.calculator.PackageManager
import org.example.data.model.OfferModel
import org.example.data.model.OrderModel
import org.example.domain.repository.CourierLocalRepository

class CourierUseCaseImpl(
    private val courierRepository: CourierLocalRepository,
    private val packageManager: PackageManager,
) : CourierUseCase {
    override fun getOffer(): List<OfferModel> {
        return courierRepository.getOffers()
    }

    override fun calculateCostAndTime(orderModel: OrderModel): OrderModel {
        val listWithTotalCost = packageManager.checkOfferAndCalculateTotal(
            packageList = orderModel.packageList,
            baseCost = orderModel.baseCost,
            offerList = courierRepository.getOffers(),
        )

        val matchedPackage = packageManager.matchPackageMaximizeWeight(
            packageList = listWithTotalCost,
            maximumCarryWeight = orderModel.maximumCarryWeight,
        )

        val resultPackage = packageManager.calculateTimeRequiredForEachPackage(
            matchedPackage = matchedPackage,
            speedLimit = orderModel.maximumSpeedLimit,
            numberOfFleet = orderModel.numberOfCar,
        )

        return orderModel.copy(
            packageList = orderModel.packageList.map { defaultPackage ->
                val tempPackage = resultPackage.first { it.packageId == defaultPackage.packageId }
                defaultPackage.copy(
                    offerApplied = tempPackage.offerApplied,
                    totalDiscount = tempPackage.totalDiscount,
                    totalCost = tempPackage.totalCost,
                    deliveryTime = tempPackage.deliveryTime,
                )
            }
        )
    }
}