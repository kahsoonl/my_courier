package timeestimation

import org.example.data.model.OfferModel
import org.example.data.model.PackageModel
import org.example.timeestimation.TimeEstimationImpl
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeEstimationImplTest {
    private val _underTest = TimeEstimationImpl()

    @Test
    fun `given a set of matched package, speed limit and number of fleet return valid estimated delivery time`() {
        // Given
        val matchedPackage: List<List<PackageModel>> = listOf(
            listOf(
                PackageModel(
                    packageId = "PKG2",
                    packageWeight = 75,
                    packageDistance = 125,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR008",
                    totalDiscount = 0,
                    totalCost = 1475,
                ),
                PackageModel(
                    packageId = "PKG4",
                    packageWeight = 110,
                    packageDistance = 60,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR002",
                    totalDiscount = 105,
                    totalCost = 1395,
                ),
            ),
            listOf(
                PackageModel(
                    packageId = "PKG3",
                    packageWeight = 175,
                    packageDistance = 100,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR003",
                    totalDiscount = 0,
                    totalCost = 2350,
                ),
            ),
            listOf(
                PackageModel(
                    packageId = "PKG5",
                    packageWeight = 155,
                    packageDistance = 95,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "NA",
                    totalDiscount = 0,
                    totalCost = 2125,
                )
            ),
            listOf(
                PackageModel(
                    packageId = "PKG1",
                    packageWeight = 50,
                    packageDistance = 30,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR001",
                    totalDiscount = 0,
                    totalCost = 750,
                ),
            )
        )
        val speedLimit = 70
        val numberOfFleet = 2

        // When
        val result = _underTest.calculateTimeRequired(matchedPackage, speedLimit, numberOfFleet)
        //time

        // Then
        val expected = listOf(
            PackageModel(
                packageId = "PKG2",
                packageWeight = 75,
                packageDistance = 125,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR008",
                totalDiscount = 0,
                totalCost = 1475,
                deliveryTime = 1.78,
            ),
            PackageModel(
                packageId = "PKG4",
                packageWeight = 50,
                packageDistance = 30,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR002",
                totalDiscount = 105,
                totalCost = 1395,
                deliveryTime = 0.85,
            ),
            PackageModel(
                packageId = "PKG3",
                packageWeight = 175,
                packageDistance = 100,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR003",
                totalDiscount = 0,
                totalCost = 2350,
                deliveryTime = 1.42,
            ),
            PackageModel(
                packageId = "PKG5",
                packageDistance = 95,
                offerApplied = OfferModel.emptyValue,
                offerCode = "NA",
                totalDiscount = 0,
                totalCost = 2125,
                deliveryTime = 4.19,
            ),
            PackageModel(
                packageId = "PKG1",
                packageWeight = 50,
                packageDistance = 30,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR001",
                totalDiscount = 0,
                totalCost = 750,
                deliveryTime = 3.98,
            ),
        )
        assertEquals(expected.toString(), result.toString())
    }
}