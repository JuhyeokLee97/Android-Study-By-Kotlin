package com.example.bottomsheetdialogfragmentexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.bottomsheetdialogfragmentexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ProductAdapter(this)
        binding.recyclerView.adapter = adapter
        adapter.submitList(getProducts())

    }

    private fun getProducts(): ArrayList<ProductModel> {
        var list = ArrayList<ProductModel>()

        list.add(ProductModel(id = 1, name ="상품 1", price ="19,900원", date ="2022.03.01", cnt = 1, option ="옵션-1"))
        list.add(ProductModel(id = 2, name ="상품 2", price ="29,900원", date ="2022.03.01", cnt = 2, option ="옵션-1"))
        list.add(ProductModel(id = 3, name ="상품 3", price ="39,900원", date ="2022.03.02", cnt = 3, option ="옵션-2"))
        list.add(ProductModel(id = 4, name ="상품 4", price ="49,900원", date ="2022.03.03", cnt = 4, option ="옵션-3"))

        return list
    }
}