package org.example.timeestimation

import org.example.data.model.PackageModel
import java.math.BigDecimal
import java.math.RoundingMode

class TimeEstimationImpl : TimeEstimation {
    override fun calculateTimeRequired(
        matchedPackage: List<List<PackageModel>>,
        speedLimit: Int,
        numberOfFleet: Int
    ): List<PackageModel> {
        val fleetArray : Array<Double> = Array(numberOfFleet) { 0.0 }
        val mutableMatchedPackage : MutableList<List<PackageModel>> = matchedPackage.toMutableList()
        var tempCount = 0
        val resultPackage: MutableList<PackageModel> = mutableListOf()

        while (mutableMatchedPackage.isNotEmpty()) {
            if(fleetArray.isNotEmpty()) {
                fleetArray.sort()
                val initialDeliveryTime = fleetArray.first()
                var longestDeliveryTime = 0.0

                matchedPackage[tempCount].forEach { packageItem ->
                    if(speedLimit != 0) {
                        val deliveryTime = BigDecimal(packageItem.packageDistance.toDouble() / speedLimit.toDouble()).setScale(2, RoundingMode.FLOOR).toDouble()

                        resultPackage.add(
                            packageItem.copy(
                                deliveryTime = initialDeliveryTime + deliveryTime
                            )
                        )

                        if(longestDeliveryTime < deliveryTime) {
                            longestDeliveryTime = deliveryTime
                        }
                    } else {
                        resultPackage.add(
                            packageItem.copy(
                                deliveryTime = 0.0
                            )
                        )
                    }
                }
                fleetArray[0] = fleetArray[0] + (longestDeliveryTime * 2)
            } else {
                matchedPackage[tempCount].forEach { packageItem ->
                    resultPackage.add(
                        packageItem
                    )
                }
            }
            mutableMatchedPackage.remove(matchedPackage[tempCount])
            tempCount++
        }

        return resultPackage
    }
}