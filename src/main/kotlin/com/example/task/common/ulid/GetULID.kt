package com.example.task.common.ulid

fun getULID(): String {
    val ulid = ULID()
    return ulid.nextMonotonicValue(ulid.nextValue()).toString()
}
