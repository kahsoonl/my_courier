package org.example.data.model

import org.example.common.emptyValue

data class OfferModel(
    val offerId: String = String.emptyValue(),
    val offerMinDistance: Int = Int.emptyValue(),
    val offerMaxDistance: Int = Int.emptyValue(),
    val offerMinWeight: Int = Int.emptyValue(),
    val offerMaxWeight: Int = Int.emptyValue(),
    val offerDiscountPercentage: Int = Int.emptyValue(),
) {
    companion object {
        val emptyValue = OfferModel()
    }

    override fun toString(): String {
        return String.format(
            "%s %d %d %d %d %d",
            offerId,
            offerMinDistance,
            offerMaxDistance,
            offerMinWeight,
            offerMaxWeight,
            offerDiscountPercentage,
        )
    }
}