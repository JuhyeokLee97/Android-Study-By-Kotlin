package com.example.viewmodelbasicsimple.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Int,
)

data class ProductDto(
    @SerializedName("items") val items: ArrayList<Product>,
)