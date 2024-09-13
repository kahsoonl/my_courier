package org.example.weightmatcher

import org.example.data.model.PackageModel

interface PackageWeightMatcher {
    fun weightMatcher(packageList: List<PackageModel>, weightLimit: Int): List<List<PackageModel>>
}