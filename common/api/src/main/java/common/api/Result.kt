package common.api

import common.api.events.Status as BaseError


sealed interface Result<I: BaseError, out D> {
    data class Error<I: BaseError, out D>(val error: I, val data: D? = null): Result<I, D>
    data class Success<I: BaseError, out D>(val success: I, val data: D): Result<I, D>
}