package com.example.productpromotionapp.models

import com.google.gson.annotations.SerializedName

class ProductList {
    @SerializedName("products")
    var plist : List<Product?>? = null
}