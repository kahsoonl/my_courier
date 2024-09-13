package org.example.offervalidation

import org.example.data.model.OfferModel
import org.example.data.model.PackageModel

class OfferValidatorImpl : OfferValidator {
    override fun validateOfferCodeApplied(packageModel: PackageModel, offerList: List<OfferModel>): OfferModel {
        val verifiedOffer = offerList.firstOrNull { offer ->
            offer.offerId == packageModel.offerCode &&
                    packageModel.packageWeight >= offer.offerMinWeight &&
                    packageModel.packageWeight <= offer.offerMaxWeight &&
                    packageModel.packageDistance >= offer.offerMinDistance &&
                    packageModel.packageDistance <= offer.offerMaxDistance
        } ?: OfferModel.emptyValue
        return verifiedOffer
    }
}