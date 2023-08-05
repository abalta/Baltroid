/*
 * Copyright 2022 Maximillian Leonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baltroid.core.common.result

import kotlinx.coroutines.flow.Flow

interface BaltroidResultHandler<T> {
    fun onLoading(block: (T?) -> Unit)
    fun onSuccess(block: (T) -> Unit)
    fun onFailure(block: (Throwable) -> Unit)
}

fun <T> BaltroidResult<T>.handle(builder: BaltroidResultHandler<T>.() -> Unit) {
    val resultHandler = object : BaltroidResultHandler<T> {
        override fun onLoading(block: (T?) -> Unit) {
            if (isLoading()) block(value)
        }

        override fun onSuccess(block: (T) -> Unit) {
            if (isSuccess()) block(value)
        }

        override fun onFailure(block: (Throwable) -> Unit) {
            if (isFailure()) block(error)
        }
    }

    builder(resultHandler)
}

suspend fun <T> Flow<BaltroidResult<T>>.handle(builder: BaltroidResultHandler<T>.() -> Unit) =
    collect { result -> result.handle(builder = builder) }
