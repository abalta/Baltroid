package com.baltroid.core.common

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Singleton

@Singleton
class EventBus {
    private val _events = MutableSharedFlow<AppEvent>()
    val events = _events.asSharedFlow()

    suspend fun invokeEvent(event: AppEvent) = _events.emit(event)
}

enum class AppEvent {
    LOGIN
}