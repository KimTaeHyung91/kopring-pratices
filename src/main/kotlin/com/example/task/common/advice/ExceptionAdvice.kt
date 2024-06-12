package com.example.task.common.advice

import com.example.task.common.exception.BusinessException
import com.example.task.common.response.Response
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ExceptionAdvice {
    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handlerBusinessException(e: BusinessException): Response.Fail<Any> {
        return Response.fail(e.message!!)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerMethodArgumentNotValidException(
        e: MethodArgumentNotValidException,
        bindingResult: BindingResult,
    ): Response.Fail<Any> {
        return Response.fail(message = bindingResult.fieldError?.defaultMessage ?: ErrorMessage.VALIDATION_ERROR.message)
    }
}
