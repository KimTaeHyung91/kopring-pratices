package com.example.task.common.response

import com.example.task.common.advice.ErrorMessage

enum class Result {
    SUCCESS,
    FAIL,
}

open class Response<out T> {
    data class Success<T>(val message: String? = null, val result: Result = Result.SUCCESS, val data: T? = null) : Response<T>()

    data class Fail<T>(val message: String, val result: Result = Result.FAIL) : Response<T>()

    data class Empty<T>(val message: String? = null, val result: Result = Result.SUCCESS, val data: T? = null)

    companion object {
        fun <T> success(
            message: String? = null,
            data: T,
        ): Success<T> = Success(message = message, data = data)

        fun empty(): Empty<Nothing> = Empty(message = null, data = null)

        fun <T> fail(message: String = ErrorMessage.INTERNAL_SERVER_ERROR.message): Fail<T> = Fail(message = message)
    }
}
