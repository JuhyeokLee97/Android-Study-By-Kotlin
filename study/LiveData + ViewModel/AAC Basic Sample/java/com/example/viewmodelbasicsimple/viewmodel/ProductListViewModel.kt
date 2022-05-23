package com.example.viewmodelbasicsimple.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodelbasicsimple.model.Product
import com.example.viewmodelbasicsimple.model.ProductDto
import com.example.viewmodelbasicsimple.service.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductListViewModel : ViewModel() {
    private val _products: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>().also {
            loadProducts()
        }
    }


    val products: LiveData<List<Product>>
        get() = _products

    private fun loadProducts() {
        // REST API retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ProductService::class.java).getProducts()
            .enqueue(object : Callback<ProductDto> {
                override fun onResponse(
                    call: Call<ProductDto>,
                    response: Response<ProductDto>,
                ) {
                    if (!response.isSuccessful) {
                        Log.d("ProductListViewModel", "onResponse: Response Fail")
                        return
                    }

                    response.body()?.let {
                        _products.postValue(it.items)
                        it.items.forEachIndexed { idx, product ->
                            Log.d("ProductListViewModel", "PRODUCT[$idx]: ${product.name}")
                        }
                    }
                }

                override fun onFailure(call: Call<ProductDto>, t: Throwable) {
                    Log.d("ProductListViewModel", "onFailure: $t")

                }
            })
    }

    fun getProduct(text: String): ArrayList<Product> {

        return ArrayList<Product>();
    }


}