package offervalidation

import org.example.data.model.OfferModel
import org.example.data.model.PackageModel
import org.example.offervalidation.OfferValidatorImpl
import kotlin.test.Test
import kotlin.test.assertEquals

class OfferValidatorImplTest {
    private val _underTest = OfferValidatorImpl()

    @Test
    fun `given valid offer code returned result should be a valid offer`() {
        // Given
        val packageModel = PackageModel(
            packageId = "PKG3",
            packageWeight = 10,
            packageDistance = 100,
            offerCode = "OFR003"
        )
        val offerList = listOf(
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

        // When
        val result = _underTest.validateOfferCodeApplied(
            packageModel = packageModel,
            offerList = offerList
        )

        // Then
        val expectedResult = OfferModel(
            offerId = "OFR003",
            offerMinDistance = 50,
            offerMaxDistance = 250,
            offerMinWeight = 10,
            offerMaxWeight = 150,
            offerDiscountPercentage = 5,
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun `given valid offer code but package doesn't meet the offer condition returned result should be an invalid offer`() {
        // Given
        val packageModel = PackageModel(
            packageId = "PKG1",
            packageWeight = 5,
            packageDistance = 5,
            offerCode = "OFR001"
        )
        val offerList = listOf(
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

        // When
        val result = _underTest.validateOfferCodeApplied(
            packageModel = packageModel,
            offerList = offerList
        )

        // Then
        val expectedResult =  OfferModel.emptyValue
        assertEquals(expectedResult, result)
    }

    @Test
    fun `given invalid offer code but package meet the offer condition returned result should be an invalid offer`() {
        // Given
        val packageModel = PackageModel(
            packageId = "PKG3",
            packageWeight = 10,
            packageDistance = 100,
            offerCode = "OFR008"
        )
        val offerList = listOf(
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

        // When
        val result = _underTest.validateOfferCodeApplied(
            packageModel = packageModel,
            offerList = offerList
        )

        // Then
        val expectedResult = OfferModel.emptyValue
        assertEquals(expectedResult, result)
    }

    @Test
    fun `given empty package returned result should be an empty offer`() {
        // Given
        val packageModel = PackageModel(
            packageId = "",
            packageWeight = 0,
            packageDistance = 0,
            offerCode = ""
        )
        val offerList = listOf(
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

        // When
        val result = _underTest.validateOfferCodeApplied(
            packageModel = packageModel,
            offerList = offerList
        )

        // Then
        val expectedResult = OfferModel.emptyValue
        assertEquals(expectedResult, result)
    }
}