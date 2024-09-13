package org.example.timeestimation

import org.example.data.model.PackageModel

interface TimeEstimation {
    fun calculateTimeRequired(matchedPackage: List<List<PackageModel>>, speedLimit: Int, numberOfFleet: Int): List<PackageModel>
}