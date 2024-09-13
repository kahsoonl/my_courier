package calculator

import org.example.data.model.PackageModel

class CostCalculatorImpl : CostCalculator {
    override fun calculateBaseCost(packageItem: PackageModel, baseCost: Int): Pair<Int, Int> {
        val baseDeliveryCost = baseCost + (packageItem.packageWeight * 10) + (packageItem.packageDistance * 5)
        val discount = (baseDeliveryCost * (packageItem.offerApplied.offerDiscountPercentage / 100.0)).toInt()
        return Pair(baseDeliveryCost - discount, discount)
    }
}