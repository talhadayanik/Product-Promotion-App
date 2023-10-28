package com.example.productpromotionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class activity_details : AppCompatActivity() {

    lateinit var img : ImageView
    lateinit var txtTitle : TextView
    lateinit var txtRate : TextView
    lateinit var txtDesc : TextView
    lateinit var txtBrand : TextView
    lateinit var txtCategory: TextView
    lateinit var txtStock : TextView
    lateinit var txtDiscount : TextView
    lateinit var txtPrice : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        img = findViewById(R.id.imgDetail)
        txtTitle = findViewById(R.id.txtDetailTitle)
        txtRate = findViewById(R.id.txtDetailRate)
        txtDesc = findViewById(R.id.txtDetailDesc)
        txtBrand = findViewById(R.id.txtDetailBrand)
        txtCategory = findViewById(R.id.txtDetailCategory)
        txtStock = findViewById(R.id.txtDetailStock)
        txtDiscount = findViewById(R.id.txtDetailDiscount)
        txtPrice = findViewById(R.id.txtDetailPrice)

        Glide.with(this).load(activity_products.selectedProduct.images.get(0)).into(img)
        txtTitle.text = activity_products.selectedProduct.title
        txtRate.text = "${activity_products.selectedProduct.rating} / 5"
        txtDesc.text = activity_products.selectedProduct.description
        txtBrand.text = "Marka: ${activity_products.selectedProduct.brand}"
        txtCategory.text = "Kategori: ${activity_products.selectedProduct.category}"
        txtStock.text = "Stok: ${activity_products.selectedProduct.stock}"
        txtDiscount.text = "%${activity_products.selectedProduct.discountPercentage} indirim"
        txtPrice.text = "${activity_products.selectedProduct.price} TL"
    }
}