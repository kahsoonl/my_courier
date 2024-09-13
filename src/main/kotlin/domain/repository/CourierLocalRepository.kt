package org.example.domain.repository

import org.example.data.model.OfferModel

interface CourierLocalRepository {
    fun getOffers(): List<OfferModel>
}