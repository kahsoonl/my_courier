package org.example.domain

import org.example.data.model.OfferModel
import org.example.data.model.OrderModel

interface CourierUseCase {
    fun getOffer(): List<OfferModel>
    fun calculateCostAndTime(orderModel: OrderModel): OrderModel
}