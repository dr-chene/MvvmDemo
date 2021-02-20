package com.dr_chene.official_demo

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * created by dr_chene on 2021/2/20
 * desc
 */
interface WebService {

    @GET("/users/{user}")
    fun getUser(@Path("user") userId: String): Flow<ApiResponse<User>>
}