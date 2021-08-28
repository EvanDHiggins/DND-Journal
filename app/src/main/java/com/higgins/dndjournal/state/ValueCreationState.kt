package com.higgins.dndjournal.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Encapsulates the state transitions which signal to observers that the user is entering some data.
 *
 * Typical flow is:
 *
 * 1. Construct and store in your view model.
 * 2. Call begin() and pass whatever contextual data is needed for finishing. This often looks like
 *    a database foreign key.
 * 3. Call finish with the entered value OR call cancel() to reset the stored state.
 *
 * I fell into the pattern several times of creating a boolean state variable with begin, finish,
 * and cancel methods to manage state transitions while the user was entering a new value (a new
 * campaign or journal, for instance).
 */
class ValueCreationStateWithContext<T, Ctx>(
    private val coroutineScope: CoroutineScope,
    private val _finish: (suspend (T, Ctx) -> Unit)
) {
    private val _active = MutableLiveData<Boolean>(false)
    val active: LiveData<Boolean> = _active

    var completionContext: Ctx? = null

    fun begin(context: Ctx? = null) {
        completionContext = context
        _active.value = true
    }

    fun finish(completedValue: T) {
        if (_active.value != true) return

        coroutineScope.launch {
            _finish(completedValue, completionContext!!)
            _active.value = false
        }
    }

    fun cancel() {
        completionContext = null
        _active.value = false
    }
}

/**
 * Same concept as ValueCreationStateWithContext without the ability to pass additional context.
 */
class ValueCreationState<T>(
    private val coroutineScope: CoroutineScope,
    private val _finish: (suspend (T) -> Unit)
) {
    private val _active = MutableLiveData<Boolean>(false)
    val active: LiveData<Boolean> = _active

    fun begin() {
        println("========================\n\n\n\n\n\n=====================")
        _active.value = true
    }

    fun finish(completedValue: T) {
        if (_active.value != true) return

        coroutineScope.launch {
            _finish(completedValue)
            _active.value = false
        }
    }

    fun cancel() {
        _active.value = false
    }
}