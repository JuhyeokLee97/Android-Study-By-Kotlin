package com.example.bottomsheetdialogfragmentexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomsheetdialogfragmentexample.databinding.ListItemBinding

class ProductAdapter(val fa: FragmentActivity) :
    ListAdapter<ProductModel, ProductAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel) {
            binding.apply {
                product = item
                productInfoButton.setOnClickListener {
                    val productInfoFragment = ProductInfoBottomSheetDialogFragment(item)
                    productInfoFragment.show(fa.supportFragmentManager, "TAG")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ProductModel>() {
            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}