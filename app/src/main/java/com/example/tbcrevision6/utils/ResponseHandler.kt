package com.example.tbcrevision6.utils

sealed class ResponseHandler {
    class Success<T>(val result: T): ResponseHandler()
    class Error(val error: String): ResponseHandler()
    class Loader(val isLoading: Boolean): ResponseHandler()
}