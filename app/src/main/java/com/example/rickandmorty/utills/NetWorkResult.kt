package com.example.rickandmorty.utills

sealed class NetWorkResult<T>(val data: T? = null ,val message :T? = null) {
     class Success<T>(data: T?) : NetWorkResult<T>(data)
     class Error<T>(message : String?,data: T? = null) : NetWorkResult<T>()
     class Loading<T>(): NetWorkResult<T>()
}