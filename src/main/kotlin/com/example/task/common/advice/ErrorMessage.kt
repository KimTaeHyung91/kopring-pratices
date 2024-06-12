package com.example.task.common.advice

enum class ErrorMessage(val message: String) {
    INTERNAL_SERVER_ERROR("내부 서버 오류"),
    VALIDATION_ERROR("필수값 오류"),
}

enum class BusinessErrorMessage(private val _message: String) {
    TASK_NOT_FOUND("태스크 정보를 찾을 수 없습니다."),
    SUB_TASK_NOT_FOUND("하위 태스크 정보를 찾을 수 없습니다."),
    ;

    var addrInfo: String? = null

    /**
     * 일반 함수로 처리하는것보다 backing field or backing properties 로 처리하는게 조금 코틀린스러운거 같음
     */
    val message: String
        get() = addrInfo?.let { "${this._message} - cause: $addrInfo" } ?: this._message
}
