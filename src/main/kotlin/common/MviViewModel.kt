package org.example.common


interface ViewState

interface UserIntent

abstract class MviViewModel<Intent : UserIntent, State : ViewState> {

    // Create Initial State of View.
    private lateinit var initialState: State

    // Get Current State.
    val currentState: State
        get() = initialState

    fun updateState(state: State) {
        initialState = state
    }

    /**
     * Handle each Intent.
     */
    abstract fun handleIntent(intent: Intent)
}