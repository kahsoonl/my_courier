package calculator

import org.example.data.model.OfferModel
import org.example.data.model.PackageModel
import kotlin.test.Test
import kotlin.test.assertEquals

class CostCalculatorImplTest {

    private val _underTest = CostCalculatorImpl()

    @Test
    fun `given invalid offer should return base cost without any discount`() {
        // Given
        val packageItem = PackageModel(packageId = "PKG1", packageWeight = 5, packageDistance = 5, offerCode = "OFR008")
        val baseCost = 100

        // When
        val result = _underTest.calculateBaseCost(packageItem, baseCost)

        // Then
        val expectedBaseDeliveryCost = Pair(baseCost + (packageItem.packageWeight * 10) + (packageItem.packageDistance * 5), 0)
        assertEquals(expectedBaseDeliveryCost, result)
    }

    @Test
    fun `given valid offer should return base cost with discount`() {
        // Given
        val packageItem = PackageModel(
            packageId = "PKG3",
            packageWeight = 10,
            packageDistance = 100,
            offerCode = "OFR003",
            offerApplied = OfferModel(
                offerId = "OFR003",
                offerMinDistance = 50,
                offerMaxDistance = 250,
                offerMinWeight = 10,
                offerMaxWeight = 150,
                offerDiscountPercentage = 5
            )
        )
        val baseCost = 100

        // When
        val result = _underTest.calculateBaseCost(packageItem, baseCost)

        // Then
        val baseDeliveryCost = baseCost + (packageItem.packageWeight * 10) + (packageItem.packageDistance * 5)
        val discount = (baseDeliveryCost * (packageItem.offerApplied.offerDiscountPercentage / 100.0)).toInt()
        val expectedCost = Pair(baseDeliveryCost - discount, discount)
        assertEquals(expectedCost, result)
    }
}