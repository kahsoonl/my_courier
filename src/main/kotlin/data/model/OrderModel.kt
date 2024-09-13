package org.example.data.model

import org.example.common.emptyValue

data class OrderModel(
    val baseCost: Int = Int.emptyValue(),
    val numberOfItem: Int = Int.emptyValue(),
    val packageList: List<PackageModel> = mutableListOf(),
    val numberOfCar: Int = Int.emptyValue(),
    val maximumCarryWeight: Int = Int.emptyValue(),
    val maximumSpeedLimit: Int = Int.emptyValue(),
) {
    companion object {
        val emptyValue = OrderModel()
    }
}