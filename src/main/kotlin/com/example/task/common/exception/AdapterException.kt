package com.example.task.common.exception

sealed class AdapterException(message: String) : RuntimeException(message)

class ApiCallException(message: String) : AdapterException(message)
