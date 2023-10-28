package com.example.productpromotionapp.services

import com.example.productpromotionapp.models.JWTData
import com.example.productpromotionapp.models.JWTUser
import com.example.productpromotionapp.models.ProductList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DummyService {

    @POST("auth/login")
    fun login(@Body jwtUser: JWTUser) : Call<JWTData>

    @GET("products")
    fun getFirstTen(@Query("limit") limit:Int) : Call<ProductList>

    @GET("products/search")
    fun getSearched(@Query("q") q: String) : Call<ProductList>
}