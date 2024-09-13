package org.example.weightmatcher

import org.example.data.model.PackageModel

class PackageWeightMatcherImpl : PackageWeightMatcher {
    override fun weightMatcher(packageList: List<PackageModel>, weightLimit: Int): List<List<PackageModel>> {
        val sortedPackage = packageList.sortedBy { it.packageWeight }.toMutableList()

        val dpTable = IntArray(weightLimit + 1) { -1 }
        dpTable[0] = 0

        val pairedPackage = Array(weightLimit + 1) { mutableListOf<PackageModel>() }
        pairedPackage[0] = mutableListOf()

        for (packageItem in sortedPackage) {
            val weight = packageItem.packageWeight
            // Traverse dp table backwards to prevent overwriting previous states
            for (w in weightLimit downTo weight) {
                if (dpTable[w - weight] != -1) {
                    // Check if including this package leads to more packages or same number of packages but with higher total weight
                    if (dpTable[w] < dpTable[w - weight] + 1) {
                        dpTable[w] = dpTable[w - weight] + 1
                        pairedPackage[w] = pairedPackage[w - weight].toMutableList().apply { add(packageItem) }
                    } else if (dpTable[w] == dpTable[w - weight] + 1) {
                        // Check if this permutation has a heavier total weight
                        if (pairedPackage[w].sumOf { it.packageWeight } < pairedPackage[w - weight].sumOf { it.packageWeight } + weight) {
                            pairedPackage[w] = pairedPackage[w - weight].toMutableList().apply { add(packageItem) }
                        }
                    }
                }
            }
        }

        val resultPackage : MutableList<List<PackageModel>> = mutableListOf()

        // Traverse from the back to get the highest sum weight
        for(w in weightLimit downTo 0) {
            if(dpTable[w] > 0) {
                resultPackage.add(pairedPackage[w])
                break
            }
        }

        return resultPackage
    }
}