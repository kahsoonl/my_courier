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

    @Test
    fun `given a set of incorrectly matched package, speed limit and number of fleet return estimated delivery time based on the list`() {
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
            ),
            listOf(
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
                deliveryTime = 3.12,
            ),
            PackageModel(
                packageId = "PKG5",
                packageDistance = 95,
                offerApplied = OfferModel.emptyValue,
                offerCode = "NA",
                totalDiscount = 0,
                totalCost = 2125,
                deliveryTime = 4.91,
            ),
            PackageModel(
                packageId = "PKG1",
                packageWeight = 50,
                packageDistance = 30,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR001",
                totalDiscount = 0,
                totalCost = 750,
                deliveryTime = 4.96,
            ),
        )
        assertEquals(expected.toString(), result.toString())
    }

    @Test
    fun `given a set of package with no weight and distance, speed limit and number of fleet return estimated delivery time based on the list`() {
        // Given
        val matchedPackage: List<List<PackageModel>> = listOf(
            listOf(
                PackageModel(
                    packageId = "PKG2",
                    packageWeight = 0,
                    packageDistance = 0,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR008",
                    totalDiscount = 0,
                    totalCost = 1475,
                ),
            ),
            listOf(
                PackageModel(
                    packageId = "PKG4",
                    packageWeight = 0,
                    packageDistance = 0,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR002",
                    totalDiscount = 105,
                    totalCost = 1395,
                ),
            ),
            listOf(
                PackageModel(
                    packageId = "PKG3",
                    packageWeight = 0,
                    packageDistance = 0,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR003",
                    totalDiscount = 0,
                    totalCost = 2350,
                ),
            ),
            listOf(
                PackageModel(
                    packageId = "PKG5",
                    packageWeight = 0,
                    packageDistance = 0,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "NA",
                    totalDiscount = 0,
                    totalCost = 2125,
                )
            ),
            listOf(
                PackageModel(
                    packageId = "PKG1",
                    packageWeight = 0,
                    packageDistance = 0,
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
                packageWeight = 0,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR008",
                totalDiscount = 0,
                totalCost = 1475,
                deliveryTime = 0.0,
            ),
            PackageModel(
                packageId = "PKG4",
                packageWeight = 0,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR002",
                totalDiscount = 105,
                totalCost = 1395,
                deliveryTime = 0.0,
            ),
            PackageModel(
                packageId = "PKG3",
                packageWeight = 0,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR003",
                totalDiscount = 0,
                totalCost = 2350,
                deliveryTime = 0.0,
            ),
            PackageModel(
                packageId = "PKG5",
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "NA",
                totalDiscount = 0,
                totalCost = 2125,
                deliveryTime = 0.0,
            ),
            PackageModel(
                packageId = "PKG1",
                packageWeight = 0,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR001",
                totalDiscount = 0,
                totalCost = 750,
                deliveryTime = 0.0,
            ),
        )
        assertEquals(expected.toString(), result.toString())
    }

    @Test
    fun `given a set of package with no distance, speed limit and number of fleet return estimated delivery time based on the list`() {
        // Given
        val matchedPackage: List<List<PackageModel>> = listOf(
            listOf(
                PackageModel(
                    packageId = "PKG2",
                    packageWeight = 75,
                    packageDistance = 0,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR008",
                    totalDiscount = 0,
                    totalCost = 1475,
                ),
                PackageModel(
                    packageId = "PKG4",
                    packageWeight = 110,
                    packageDistance = 0,
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
                    packageDistance = 0,
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
                    packageDistance = 0,
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
                    packageDistance = 0,
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
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR008",
                totalDiscount = 0,
                totalCost = 1475,
                deliveryTime = 0.0,
            ),
            PackageModel(
                packageId = "PKG4",
                packageWeight = 50,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR002",
                totalDiscount = 105,
                totalCost = 1395,
                deliveryTime = 0.0,
            ),
            PackageModel(
                packageId = "PKG3",
                packageWeight = 175,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR003",
                totalDiscount = 0,
                totalCost = 2350,
                deliveryTime = 0.0,
            ),
            PackageModel(
                packageId = "PKG5",
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "NA",
                totalDiscount = 0,
                totalCost = 2125,
                deliveryTime = 0.0,
            ),
            PackageModel(
                packageId = "PKG1",
                packageWeight = 50,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR001",
                totalDiscount = 0,
                totalCost = 750,
                deliveryTime = 0.0,
            ),
        )
        assertEquals(expected.toString(), result.toString())
    }

    @Test
    fun `given a set of matched package with no weight value, speed limit and number of fleet return package list with estimated delivery time`() {
        // Given
        val matchedPackage: List<List<PackageModel>> = listOf(
            listOf(
                PackageModel(
                    packageId = "PKG2",
                    packageWeight = 0,
                    packageDistance = 125,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR008",
                    totalDiscount = 0,
                    totalCost = 1475,
                ),
                PackageModel(
                    packageId = "PKG4",
                    packageWeight = 0,
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
                    packageWeight = 0,
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
                    packageWeight = 0,
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
                    packageWeight = 0,
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
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR008",
                totalDiscount = 0,
                totalCost = 1475,
                deliveryTime = 1.78,
            ),
            PackageModel(
                packageId = "PKG4",
                packageWeight = 50,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR002",
                totalDiscount = 105,
                totalCost = 1395,
                deliveryTime = 0.85,
            ),
            PackageModel(
                packageId = "PKG3",
                packageWeight = 175,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR003",
                totalDiscount = 0,
                totalCost = 2350,
                deliveryTime = 1.42,
            ),
            PackageModel(
                packageId = "PKG5",
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "NA",
                totalDiscount = 0,
                totalCost = 2125,
                deliveryTime = 4.19,
            ),
            PackageModel(
                packageId = "PKG1",
                packageWeight = 50,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR001",
                totalDiscount = 0,
                totalCost = 750,
                deliveryTime = 3.98,
            ),
        )
        assertEquals(expected.toString(), result.toString())
    }

    @Test
    fun `given a set of matched package but invalid speed limit and number of fleet return package list with 0 estimated delivery time`() {
        // Given
        val matchedPackage: List<List<PackageModel>> = listOf(
            listOf(
                PackageModel(
                    packageId = "PKG2",
                    packageWeight = 0,
                    packageDistance = 125,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR008",
                    totalDiscount = 0,
                    totalCost = 1475,
                ),
                PackageModel(
                    packageId = "PKG4",
                    packageWeight = 0,
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
                    packageWeight = 0,
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
                    packageWeight = 0,
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
                    packageWeight = 0,
                    packageDistance = 30,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR001",
                    totalDiscount = 0,
                    totalCost = 750,
                ),
            )
        )
        val speedLimit = 0
        val numberOfFleet = 0

        // When
        val result = _underTest.calculateTimeRequired(matchedPackage, speedLimit, numberOfFleet)
        //time

        // Then
        val expected = listOf(
            PackageModel(
                packageId = "PKG2",
                packageWeight = 75,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR008",
                totalDiscount = 0,
                totalCost = 1475,
                deliveryTime = 0.00,
            ),
            PackageModel(
                packageId = "PKG4",
                packageWeight = 50,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR002",
                totalDiscount = 105,
                totalCost = 1395,
                deliveryTime = 0.00,
            ),
            PackageModel(
                packageId = "PKG3",
                packageWeight = 175,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR003",
                totalDiscount = 0,
                totalCost = 2350,
                deliveryTime = 0.00,
            ),
            PackageModel(
                packageId = "PKG5",
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "NA",
                totalDiscount = 0,
                totalCost = 2125,
                deliveryTime = 0.00,
            ),
            PackageModel(
                packageId = "PKG1",
                packageWeight = 50,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR001",
                totalDiscount = 0,
                totalCost = 750,
                deliveryTime = 0.00,
            ),
        )
        assertEquals(expected.toString(), result.toString())
    }

    @Test
    fun `given a set of matched package and number of fleet but invalid speed limit return package list with 0 estimated delivery time`() {
        // Given
        val matchedPackage: List<List<PackageModel>> = listOf(
            listOf(
                PackageModel(
                    packageId = "PKG2",
                    packageWeight = 0,
                    packageDistance = 125,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR008",
                    totalDiscount = 0,
                    totalCost = 1475,
                ),
                PackageModel(
                    packageId = "PKG4",
                    packageWeight = 0,
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
                    packageWeight = 0,
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
                    packageWeight = 0,
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
                    packageWeight = 0,
                    packageDistance = 30,
                    offerApplied = OfferModel.emptyValue,
                    offerCode = "OFR001",
                    totalDiscount = 0,
                    totalCost = 750,
                ),
            )
        )
        val speedLimit = 0
        val numberOfFleet = 2

        // When
        val result = _underTest.calculateTimeRequired(matchedPackage, speedLimit, numberOfFleet)
        //time

        // Then
        val expected = listOf(
            PackageModel(
                packageId = "PKG2",
                packageWeight = 75,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR008",
                totalDiscount = 0,
                totalCost = 1475,
                deliveryTime = 0.00,
            ),
            PackageModel(
                packageId = "PKG4",
                packageWeight = 50,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR002",
                totalDiscount = 105,
                totalCost = 1395,
                deliveryTime = 0.00,
            ),
            PackageModel(
                packageId = "PKG3",
                packageWeight = 175,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR003",
                totalDiscount = 0,
                totalCost = 2350,
                deliveryTime = 0.00,
            ),
            PackageModel(
                packageId = "PKG5",
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "NA",
                totalDiscount = 0,
                totalCost = 2125,
                deliveryTime = 0.00,
            ),
            PackageModel(
                packageId = "PKG1",
                packageWeight = 50,
                packageDistance = 0,
                offerApplied = OfferModel.emptyValue,
                offerCode = "OFR001",
                totalDiscount = 0,
                totalCost = 750,
                deliveryTime = 0.00,
            ),
        )
        assertEquals(expected.toString(), result.toString())
    }

    @Test
    fun `given an empty list, should return empty list`() {
        // Given
        val matchedPackage: List<List<PackageModel>> = listOf()
        val speedLimit = 70
        val numberOfFleet = 2

        // When
        val result = _underTest.calculateTimeRequired(matchedPackage, speedLimit, numberOfFleet)
        //time

        // Then
        val expected: List<List<PackageModel>> = listOf()
        assertEquals(expected.toString(), result.toString())
    }
}