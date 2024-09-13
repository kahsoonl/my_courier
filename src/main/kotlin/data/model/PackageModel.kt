package org.example.data.model

import org.example.common.emptyValue

data class PackageModel(
    val packageId: String = String.emptyValue(),
    val packageWeight: Int = Int.emptyValue(),
    val packageDistance: Int = Int.emptyValue(),
    val offerApplied: OfferModel = OfferModel.emptyValue,
    val offerCode: String = String.emptyValue(),
    val totalDiscount: Int = Int.emptyValue(),
    val totalCost: Int = Int.emptyValue(),
    val deliveryTime: Double = Double.emptyValue(),
) {
    companion object {
        val emptyValue = PackageModel()
    }

    override fun toString(): String {
        return String.format(
            "%s %d %d %.2f",
            packageId,
            totalDiscount,
            totalCost,
            deliveryTime,
        )
    }
}