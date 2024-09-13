package org.example.data.repository

import org.example.data.model.OfferModel
import org.example.domain.repository.CourierLocalRepository

class CourierLocalRepositoryImpl : CourierLocalRepository {
    override fun getOffers(): List<OfferModel> {
        return listOf(
            OfferModel(
                offerId = "OFR001",
                offerMinDistance = 0,
                offerMaxDistance = 200,
                offerMinWeight = 70,
                offerMaxWeight = 200,
                offerDiscountPercentage = 10,
            ),
            OfferModel(
                offerId = "OFR002",
                offerMinDistance = 50,
                offerMaxDistance = 150,
                offerMinWeight = 100,
                offerMaxWeight = 250,
                offerDiscountPercentage = 7,
            ),
            OfferModel(
                offerId = "OFR003",
                offerMinDistance = 50,
                offerMaxDistance = 250,
                offerMinWeight = 10,
                offerMaxWeight = 150,
                offerDiscountPercentage = 5,
            ),
        )
    }
}