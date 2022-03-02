package com.example.bottomsheetdialogfragmentexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottomsheetdialogfragmentexample.databinding.FragmentProductInfoBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductInfoBottomSheetDialogFragment(val item: ProductModel) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentProductInfoBottomSheetDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            FragmentProductInfoBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            product = item
            closeButton.setOnClickListener {
                dismiss()
            }
        }

    }
}