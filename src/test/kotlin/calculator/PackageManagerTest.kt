package calculator

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.example.calculator.PackageManager
import org.example.data.model.OfferModel
import org.example.data.model.PackageModel
import org.example.offervalidation.OfferValidator
import org.example.timeestimation.TimeEstimation
import org.example.weightmatcher.PackageWeightMatcher
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PackageManagerTest {
    private val costCalculator: CostCalculator = mockk()
    private val offerValidator: OfferValidator = mockk()
    private val weightMatcher: PackageWeightMatcher = mockk()
    private val timeEstimation: TimeEstimation = mockk()
    private lateinit var _underTest: PackageManager

    private val offerList = listOf(
        OfferModel(
            offerId = "OFR001",
            offerMinDistance = 0,
            offerMaxDistance = 200,
            offerMinWeight = 70,
            offerMaxWeight = 200,
            offerDiscountPercentage = 10,
        ),
        OfferModel(
            offerId = "OFR002",
            offerMinDistance = 50,
            offerMaxDistance = 150,
            offerMinWeight = 100,
            offerMaxWeight = 250,
            offerDiscountPercentage = 7,
        ),
        OfferModel(
            offerId = "OFR003",
            offerMinDistance = 50,
            offerMaxDistance = 250,
            offerMinWeight = 10,
            offerMaxWeight = 150,
            offerDiscountPercentage = 5,
        )
    )

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
        _underTest = PackageManager(
            costCalculator, offerValidator, weightMatcher, timeEstimation
        )
    }

    @Test
    fun `given a set of package with valid offer and base cost return package list with valid discount calculated`() {
        // Given
        val packageList = mutableListOf(
            PackageModel(
                packageId = "PKG3",
                packageWeight = 10,
                packageDistance = 100,
                offerCode = "OFR003"
            )
        )
        val baseCost = 100

        every {
            offerValidator.validateOfferCodeApplied(
                packageModel = any(),
                offerList = any(),
            )
        } answers {
            OfferModel(
                offerId = "OFR003",
                offerMinDistance = 50,
                offerMaxDistance = 250,
                offerMinWeight = 10,
                offerMaxWeight = 150,
                offerDiscountPercentage = 5,
            )
        }

        every {
            costCalculator.calculateBaseCost(any(), any())
        } answers {
            Pair(665, 35)
        }

        val result = _underTest.checkOfferAndCalculateTotal(packageList, baseCost, offerList)

        // Then
        val expected = listOf(
            PackageModel(
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
                    offerDiscountPercentage = 5,
                ),
                totalDiscount = 35,
                totalCost = 665,
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `given a set of package with invalid data and base cost return package list with no discount calculated`() {
        // Given
        val packageList = mutableListOf(
            PackageModel(
                packageId = "PKG3",
                packageWeight = 0,
                packageDistance = 0,
                offerCode = "OFR003"
            )
        )
        val baseCost = 0

        every {
            offerValidator.validateOfferCodeApplied(
                packageModel = any(),
                offerList = any(),
            )
        } answers {
            OfferModel.emptyValue
        }

        every {
            costCalculator.calculateBaseCost(any(), any())
        } answers {
            Pair(0, 0)
        }

        val result = _underTest.checkOfferAndCalculateTotal(packageList, baseCost, offerList)

        // Then
        val expected = listOf(
            PackageModel(
                packageId = "PKG3",
                packageWeight = 0,
                packageDistance = 0,
                offerCode = "OFR003",
                offerApplied = OfferModel.emptyValue,
                totalDiscount = 0,
                totalCost = 0,
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `given a set of empty package list should return empty package list`() {
        // Given
        val packageList: MutableList<PackageModel> = mutableListOf()
        val baseCost = 0

        every {
            offerValidator.validateOfferCodeApplied(
                packageModel = any(),
                offerList = any(),
            )
        } answers {
            OfferModel.emptyValue
        }

        every {
            costCalculator.calculateBaseCost(any(), any())
        } answers {
            Pair(0, 0)
        }

        val result = _underTest.checkOfferAndCalculateTotal(packageList, baseCost, offerList)

        // Then
        val expected: List<PackageModel> = listOf()
        assertEquals(expected, result)
    }

    @Test
    fun `given a set of package with invalid offer and base cost return package list with no discount calculated`() {
        // Given
        val packageList = mutableListOf(
            PackageModel(
                packageId = "PKG3",
                packageWeight = 10,
                packageDistance = 100,
                offerCode = "NA"
            ),
        )
        val baseCost = 100

        every {
            offerValidator.validateOfferCodeApplied(
                packageModel = any(),
                offerList = any(),
            )
        } answers {
            OfferModel.emptyValue
        }

        every {
            costCalculator.calculateBaseCost(any(), any())
        } answers {
            Pair(700, 0)
        }

        val result = _underTest.checkOfferAndCalculateTotal(packageList, baseCost, offerList)

        // Then
        val expected = listOf(
            PackageModel(
                packageId = "PKG3",
                packageWeight = 10,
                packageDistance = 100,
                offerCode = "NA",
                totalDiscount = 0,
                totalCost = 700,
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `given a set of package list and a weight limit return matched package list`() {
        // Given
        val packageList = mutableListOf(
            PackageModel(
                packageId = "PKG2",
                packageWeight = 75,
                packageDistance = 125,
            ),
            PackageModel(
                packageId = "PKG4",
                packageWeight = 110,
                packageDistance = 60,
            ),
        )
        val weightLimit = 200

        every {
            weightMatcher.weightMatcher(any(), any())
        } answers {
            listOf(
                listOf(
                    PackageModel(
                        packageId = "PKG2",
                        packageWeight = 75,
                        packageDistance = 125,
                    ),
                    PackageModel(
                        packageId = "PKG4",
                        packageWeight = 110,
                        packageDistance = 60,
                    )
                )
            )
        }

        val result = _underTest.matchPackageMaximizeWeight(packageList, weightLimit)

        // Then
        val expected = listOf(
            listOf(
                PackageModel(
                    packageId = "PKG2",
                    packageWeight = 75,
                    packageDistance = 125,
                ),
                PackageModel(
                    packageId = "PKG4",
                    packageWeight = 110,
                    packageDistance = 60,
                )
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `given a set of empty package list return empty weight matched list`() {
        // Given
        val packageList: MutableList<PackageModel> = mutableListOf()
        val weightLimit = 200

        every {
            weightMatcher.weightMatcher(any(), any())
        } answers {
            listOf()
        }

        val result = _underTest.matchPackageMaximizeWeight(packageList, weightLimit)

        // Then
        val expected: List<List<PackageModel>> = listOf()
        assertEquals(expected, result)
    }

    @Test
    fun `given a set of invalid weight package list return a matched list with single value`() {
        // Given
        val packageList = mutableListOf(
            PackageModel(
                packageId = "PKG2",
                packageWeight = 0,
                packageDistance = 125,
            ),
            PackageModel(
                packageId = "PKG4",
                packageWeight = 0,
                packageDistance = 60,
            ),
        )
        val weightLimit = 200

        every {
            weightMatcher.weightMatcher(any(), any())
        } answers {
            listOf(
                listOf(
                    PackageModel(
                        packageId = "PKG2",
                        packageWeight = 0,
                        packageDistance = 125,
                    ),
                    PackageModel(
                        packageId = "PKG4",
                        packageWeight = 0,
                        packageDistance = 60,
                    )
                )
            )
        }

        val result = _underTest.matchPackageMaximizeWeight(packageList, weightLimit)

        // Then
        val expected: List<List<PackageModel>> = listOf(
            listOf(
                PackageModel(
                    packageId = "PKG2",
                    packageWeight = 0,
                    packageDistance = 125,
                ),
                PackageModel(
                    packageId = "PKG4",
                    packageWeight = 0,
                    packageDistance = 60,
                )
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun `given a set of matched package list, speed limit and fleet number return package list with calculated time`() {
        // Given
        val packageList = listOf(
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
        )
        val speedLimit = 70
        val numberOfFleet = 2

        every {
            timeEstimation.calculateTimeRequired(any(), any(), any())
        } answers {
            listOf(
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
                )
            )
        }

        val result = _underTest.calculateTimeRequiredForEachPackage(packageList, speedLimit, numberOfFleet)

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
        )
        assertEquals(expected, result)
    }

    @Test
    fun `given a set of empty package list, speed limit and fleet number return empty package list`() {
        // Given
        val packageList: List<List<PackageModel>> = listOf(
            listOf(),
        )
        val speedLimit = 70
        val numberOfFleet = 2

        every {
            timeEstimation.calculateTimeRequired(any(), any(), any())
        } answers {
            listOf()
        }

        val result = _underTest.calculateTimeRequiredForEachPackage(packageList, speedLimit, numberOfFleet)

        // Then
        val expected: List<PackageModel> = listOf()
        assertEquals(expected, result)
    }
}