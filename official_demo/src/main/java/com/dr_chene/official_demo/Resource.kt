package com.dr_chene.official_demo

/**
 * created by dr_chene on 2021/2/20
 * desc
 */
sealed class Resource<T>(
        val data: T? = null,
        val message: String? = null
){
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
