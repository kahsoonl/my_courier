package org.example.offervalidation

import org.example.data.model.OfferModel
import org.example.data.model.PackageModel

interface OfferValidator {
    fun validateOfferCodeApplied(packageModel: PackageModel, offerList: List<OfferModel>): OfferModel
}