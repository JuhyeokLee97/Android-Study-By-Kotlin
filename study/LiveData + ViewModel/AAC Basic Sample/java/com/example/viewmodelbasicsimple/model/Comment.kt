package com.example.viewmodelbasicsimple.model

import java.util.*

data class Comment(
    val id: Int,
    val productId: Int,
    val text: String,
    val postAt: Date,
)
