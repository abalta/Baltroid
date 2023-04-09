package com.baltroid.core.common.result

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun <T> BaltroidResult<T>.isLoading(): Boolean {
    contract {
        returns() implies (this@isLoading is BaltroidResult.Loading<T>)
    }
    return this is BaltroidResult.Loading<T>
}

@OptIn(ExperimentalContracts::class)
fun <T> BaltroidResult<T>.isSuccess(): Boolean {
    contract {
        returns() implies (this@isSuccess is BaltroidResult.Success<T>)
    }
    return this is BaltroidResult.Success<T>
}

@OptIn(ExperimentalContracts::class)
fun <T> BaltroidResult<T>.isFailure(): Boolean {
    contract {
        returns() implies (this@isFailure is BaltroidResult.Failure<T>)
    }
    return this is BaltroidResult.Failure<T>
}
