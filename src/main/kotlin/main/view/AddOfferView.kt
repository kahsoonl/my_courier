package org.example.main.view

import org.example.main.viewmodel.CourierUserIntent
import org.example.main.viewmodel.CourierViewState
import java.util.*

fun AddOfferView(
    viewState: CourierViewState.AddOffer,
    userIntent: (CourierUserIntent) -> Unit,
) {
    if(viewState.message.isEmpty()) {
        println("Enter offer detail in following format\noffer_id offer_min_distance offer_max_distance offer_min_weight offer_max_weight offer_discount_percentage")
        val reader = Scanner(System.`in`)
        val offerInput = reader.nextLine()
        userIntent.invoke(
            CourierUserIntent.UserInputAddOffer(offerInput = offerInput)
        )
    } else {
        println(viewState.message)
        userIntent.invoke(CourierUserIntent.UserInputMenuSelection("2"))
    }
}