package org.example.data.repository

import org.example.data.model.OfferModel
import org.example.database.offer.OfferDatabase
import org.example.domain.repository.CourierLocalRepository

class CourierLocalRepositoryImpl(
    private val offerDatabase: OfferDatabase
) : CourierLocalRepository {
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

    override fun getOffersFromDb(): List<OfferModel> {
        return offerDatabase.getOffer()
    }

    override fun insertOfferToDb(offerModel: OfferModel): Boolean {
        return offerDatabase.insertOffer(offerModel)
    }

    override fun removeOfferFromDb(offerId: String): Boolean {
        return offerDatabase.deleteOffer(offerId)
    }

    override fun checkOfferTable() {
        offerDatabase.createOfferTable()
    }
}