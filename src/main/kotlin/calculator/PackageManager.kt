package org.example.calculator

import calculator.CostCalculator
import org.example.data.model.OfferModel
import org.example.data.model.PackageModel
import org.example.offervalidation.OfferValidator
import org.example.timeestimation.TimeEstimation
import org.example.weightmatcher.PackageWeightMatcher

class PackageManager(
    private val costCalculator: CostCalculator,
    private val offerValidator: OfferValidator,
    private val weightMatcher: PackageWeightMatcher,
    private val timeEstimation: TimeEstimation,
) {
    fun checkOfferAndCalculateTotal(packageList: List<PackageModel>, baseCost: Int, offerList: List<OfferModel>): List<PackageModel> {
        val offerAppliedPackage = packageList.map { packageItem ->
            val validatedOffer = offerValidator.validateOfferCodeApplied(
                packageModel = packageItem,
                offerList = offerList,
            )
            packageItem.copy(
                offerApplied = validatedOffer,
            )
        }

        return offerAppliedPackage.map { packageItem ->
            val totalDiscountPair = costCalculator.calculateBaseCost(packageItem, baseCost)
            packageItem.copy(
                totalCost = totalDiscountPair.first,
                totalDiscount = totalDiscountPair.second,
            )
        }
    }

    fun matchPackageMaximizeWeight(packageList: List<PackageModel>, maximumCarryWeight: Int): List<List<PackageModel>> {
        val mutablePackageList = packageList.toMutableList()
        val mutableSortedPackage : MutableList<List<PackageModel>> = mutableListOf()

        while(mutablePackageList.isNotEmpty()) {
            val resultBundle = weightMatcher.weightMatcher(
                packageList = mutablePackageList,
                weightLimit = maximumCarryWeight,
            )
            mutableSortedPackage.add(resultBundle.first())
            mutablePackageList.removeAll(resultBundle.first())
        }

        return mutableSortedPackage.toList()
    }

    fun calculateTimeRequiredForEachPackage(matchedPackage: List<List<PackageModel>>, speedLimit: Int, numberOfFleet: Int): List<PackageModel> {
        return timeEstimation.calculateTimeRequired(matchedPackage, speedLimit, numberOfFleet)
    }
}