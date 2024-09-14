package org.example.main.view

import org.example.main.viewmodel.CourierUserIntent
import org.example.main.viewmodel.CourierViewState
import java.util.*

fun DeleteOfferView(
    viewState: CourierViewState.DeleteOffer,
    userIntent: (CourierUserIntent) -> Unit,
) {
    if(viewState.message.isEmpty()) {
        println("Enter offer id that you wish to delete")
        val reader = Scanner(System.`in`)
        val offerIdInput = reader.nextLine()
        userIntent.invoke(
            CourierUserIntent.UserInputOfferIdDelete(offerId = offerIdInput)
        )
    } else {
        println(viewState.message)
        userIntent.invoke(
            CourierUserIntent.UserInputMenuSelection("2")
        )
    }

}