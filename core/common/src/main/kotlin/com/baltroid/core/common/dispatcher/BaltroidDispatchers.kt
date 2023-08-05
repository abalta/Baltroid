package com.baltroid.core.common.dispatcher

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val baltroidDispatcher: BaltroidDispatchers)

enum class BaltroidDispatchers { Default, IO }
