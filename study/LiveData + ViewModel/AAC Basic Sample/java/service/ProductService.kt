package com.example.viewmodelbasicsimple.service

import com.example.viewmodelbasicsimple.model.ProductDto
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    // https://run.mocky.io/v3/9fb33b80-8b3a-4c9f-b608-764bc60eae98
    @GET("v3/9fb33b80-8b3a-4c9f-b608-764bc60eae98")
    fun getProducts(): Call<ProductDto>
}