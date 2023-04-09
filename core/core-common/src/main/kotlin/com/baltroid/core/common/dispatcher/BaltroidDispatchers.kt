package com.baltroid.core.common.dispatcher

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val cinemaxDispatcher: BaltroidDispatchers)

enum class BaltroidDispatchers { Default, Main, Unconfined, IO }
