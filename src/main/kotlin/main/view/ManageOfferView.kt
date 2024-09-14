package org.example.main.view

import org.example.main.viewmodel.CourierUserIntent
import org.example.main.viewmodel.CourierViewState
import java.util.*

fun ManageOfferView(
    viewState: CourierViewState.ManagePromoState,
    userIntent: (CourierUserIntent) -> Unit,
) {
    println("Print format")
    println("pkg_id pkg_min_distance pkg_max_distance pkg_min_weight pkg_max_weight pkg_disc_percentage")
    if(viewState.offerList.isEmpty()) {
        println("No offer found")
    } else {
        viewState.offerList.forEach {
            println(
                it.toString()
            )
        }
    }
    println("1. Add a new offer\n2. Delete a offer via Offer ID\n3. Return to main menu")
    val reader = Scanner(System.`in`)
    val menuSelection = reader.nextLine()
    userIntent.invoke(
        CourierUserIntent.UserInputOfferMenu(offerMenuInput = menuSelection)
    )
}