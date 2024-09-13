package calculator

import org.example.data.model.PackageModel

interface CostCalculator {
    fun calculateBaseCost(packageItem: PackageModel, baseCost: Int): Pair<Int, Int>
}