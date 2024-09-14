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

    @Test
    fun `given a package with incorrectly assigned offer but does not match offer condition should return base cost with no discount`() {
        // Given
        val packageItem = PackageModel(
            packageId = "PKG3",
            packageWeight = 5,
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
        val expectedCost = Pair(baseDeliveryCost, 0)
        assertEquals(expectedCost, result)
    }

    @Test
    fun `given a package with incorrect package distance and weight should return base cost as cost`() {
        // Given
        val packageItem = PackageModel(
            packageId = "PKG3",
            packageWeight = 0,
            packageDistance = 0,
            offerCode = "ASDF",
        )
        val baseCost = 100

        // When
        val result = _underTest.calculateBaseCost(packageItem, baseCost)

        // Then
        val expectedCost = Pair(100, 0)
        assertEquals(expectedCost, result)
    }

    @Test
    fun `given a package with 0 as base cost should return final cost as 0`() {
        // Given
        val packageItem = PackageModel(
            packageId = "PKG3",
            packageWeight = 0,
            packageDistance = 0,
            offerCode = "ASDF",
        )
        val baseCost = 0

        // When
        val result = _underTest.calculateBaseCost(packageItem, baseCost)

        // Then
        val expectedCost = Pair(0, 0)
        assertEquals(expectedCost, result)
    }

    @Test
    fun `given a package with offer applied should return final cost as 0`() {
        // Given
        val packageItem = PackageModel(
            packageId = "PKG3",
            packageWeight = 0,
            packageDistance = 0,
            offerCode = "ASDF",
            offerApplied = OfferModel(
                offerId = "OFR003",
                offerMinDistance = 50,
                offerMaxDistance = 250,
                offerMinWeight = 10,
                offerMaxWeight = 150,
                offerDiscountPercentage = 5
            )
        )
        val baseCost = 0

        // When
        val result = _underTest.calculateBaseCost(packageItem, baseCost)

        // Then
        val expectedCost = Pair(0, 0)
        assertEquals(expectedCost, result)
    }
}