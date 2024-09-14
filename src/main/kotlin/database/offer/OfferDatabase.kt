package org.example.database.offer

import org.example.data.model.OfferModel

interface OfferDatabase {
    fun createOfferTable()
    fun insertOffer(offerModel: OfferModel): Boolean
    fun deleteOffer(offerId: String): Boolean
    fun getOffer(): List<OfferModel>
}