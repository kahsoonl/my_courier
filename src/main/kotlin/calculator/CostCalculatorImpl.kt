package calculator

import org.example.data.model.OfferModel
import org.example.data.model.PackageModel

class CostCalculatorImpl : CostCalculator {
    override fun calculateBaseCost(packageItem: PackageModel, baseCost: Int): Pair<Int, Int> {
        val baseDeliveryCost = baseCost + (packageItem.packageWeight * 10) + (packageItem.packageDistance * 5)
        var discount = 0
        if (packageItem.offerApplied != OfferModel.emptyValue &&
            packageItem.packageWeight >= packageItem.offerApplied.offerMinWeight &&
            packageItem.packageWeight <= packageItem.offerApplied.offerMaxWeight &&
            packageItem.packageDistance >= packageItem.offerApplied.offerMinDistance &&
            packageItem.packageDistance <= packageItem.offerApplied.offerMaxDistance) {
            discount = (baseDeliveryCost * (packageItem.offerApplied.offerDiscountPercentage / 100.0)).toInt()
        }
        return Pair(baseDeliveryCost - discount, discount)
    }
}