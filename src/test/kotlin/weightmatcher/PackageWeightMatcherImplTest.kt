package weightmatcher

import org.example.data.model.PackageModel
import org.example.weightmatcher.PackageWeightMatcherImpl
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PackageWeightMatcherImplTest {
    private val _underTest = PackageWeightMatcherImpl()

    @Test
    fun `given a set of packages return the permutation that prioritize amount and sum of weight of the bundled packages`() {
        // Given
        val packageList = listOf(
            PackageModel(
                packageId = "PKG1",
                packageWeight = 50,
                packageDistance = 30,
            ),
            PackageModel(
                packageId = "PKG2",
                packageWeight = 75,
                packageDistance = 125,
            ),
            PackageModel(
                packageId = "PKG3",
                packageWeight = 175,
                packageDistance = 100,
            ),
            PackageModel(
                packageId = "PKG4",
                packageWeight = 110,
                packageDistance = 60,
            ),
            PackageModel(
                packageId = "PKG5",
                packageWeight = 155,
                packageDistance = 95,
            )
        )
        val weightLimit = 200

        // When
        val result = _underTest.weightMatcher(packageList, weightLimit)

        // Then
        val expected =
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
        assertEquals(expected, result)
    }

    @Test
    fun `given a set of packages with invalid weight return the list of list of the same set of packages`() {
        // Given
        val weightLimit = 200
        val packageList = listOf(
            PackageModel(
                packageId = "PKG1",
                packageWeight = 0,
                packageDistance = 30,
            ),
            PackageModel(
                packageId = "PKG2",
                packageWeight = 0,
                packageDistance = 125,
            ),
            PackageModel(
                packageId = "PKG3",
                packageWeight = 0,
                packageDistance = 100,
            ),
            PackageModel(
                packageId = "PKG4",
                packageWeight = 0,
                packageDistance = 60,
            ),
            PackageModel(
                packageId = "PKG5",
                packageWeight = 0,
                packageDistance = 95,
            )
        )

        // When
        val result = _underTest.weightMatcher(packageList, weightLimit)

        // Then
        val expected =
            listOf(
                listOf(
                    PackageModel(
                        packageId = "PKG1",
                        packageWeight = 0,
                        packageDistance = 30,
                    ),
                    PackageModel(
                        packageId = "PKG2",
                        packageWeight = 0,
                        packageDistance = 125,
                    ),
                    PackageModel(
                        packageId = "PKG3",
                        packageWeight = 0,
                        packageDistance = 100,
                    ),
                    PackageModel(
                        packageId = "PKG4",
                        packageWeight = 0,
                        packageDistance = 60,
                    ),
                    PackageModel(
                        packageId = "PKG5",
                        packageWeight = 0,
                        packageDistance = 95,
                    )
                )
            )
        assertEquals(expected, result)
    }
}