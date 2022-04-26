package com.rootsid.wal.agent.exception

import com.rootsid.wal.agent.api.response.ErrorResponse
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

//https://reflectoring.io/spring-boot-exception-handling/
//https://github.com/thombergs/code-examples/blob/master/spring-boot/exception-handling/src/main/java/io/reflectoring/exception/exception/ErrorResponse.java
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    companion object {
        const val TRACE = "trace"

        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val log = LoggerFactory.getLogger(javaClass.enclosingClass)

        private val GLOBAL_EXCEPTION_HANDLER: Marker = MarkerFactory.getMarker("GLOBAL_EXCEPTION_HANDLER")
    }

    @Value("\${reflectoring.trace:false}")
    private val printStackTrace = false

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders,
                                                        status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Validation error. Check 'errors' field for details.")

        ex.bindingResult.fieldErrors.forEach {
            errorResponse.addValidationError(it.field, it.defaultMessage)
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFound(ex: ResourceNotFoundException, request: WebRequest
    ): ResponseEntity<Any> {
        log.error(GLOBAL_EXCEPTION_HANDLER, "Failed to find the requested element", ex)
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAllUncaughtException(ex: Exception, request: WebRequest
    ): ResponseEntity<Any> {
        log.error(GLOBAL_EXCEPTION_HANDLER, "Unknown error occurred", ex)
        return buildErrorResponse(ex, "Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request)
    }

    override fun handleExceptionInternal(ex: Exception, body: Any?, headers: HttpHeaders,
                                         status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        return buildErrorResponse(ex, status, request)
    }

    private fun buildErrorResponse(exception: Exception, httpStatus: HttpStatus,
                                   request: WebRequest
    ): ResponseEntity<Any> {
        return buildErrorResponse(exception, exception.message, httpStatus, request)
    }

    private fun buildErrorResponse(exception: Exception, message: String?,
                                   httpStatus: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(httpStatus.value(), message)

        if (printStackTrace && isTraceOn(request)) {
            errorResponse.stackTrace = ExceptionUtils.getStackTrace(exception)
        }
        return ResponseEntity.status(httpStatus).body(errorResponse)
    }

    private fun isTraceOn(request: WebRequest): Boolean {
        val value = request.getParameterValues(TRACE)
        return (Objects.nonNull(value)
                && value!!.isNotEmpty() && value[0]!!.contentEquals("true"))
    }
}
