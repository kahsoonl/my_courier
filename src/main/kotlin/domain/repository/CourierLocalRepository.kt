package org.example.domain.repository

import org.example.data.model.OfferModel

interface CourierLocalRepository {
    fun checkOfferTable()
    fun getOffers(): List<OfferModel>
    fun getOffersFromDb(): List<OfferModel>
    fun insertOfferToDb(offerModel: OfferModel): Boolean
    fun removeOfferFromDb(offerId: String): Boolean
}