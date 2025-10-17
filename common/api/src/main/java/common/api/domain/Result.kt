package common.api.domain

import common.api.domain.events.Status


sealed interface Result<I: Status, out D> {
    data class Error<I: Status, out D>(val error: I, val data: D? = null): Result<I, D>
    data class Success<I: Status, out D>(val success: I, val data: D): Result<I, D>
}