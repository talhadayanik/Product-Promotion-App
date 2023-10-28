package com.example.productpromotionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import com.example.productpromotionapp.adapters.ProductsAdapter
import com.example.productpromotionapp.configs.ApiClient
import com.example.productpromotionapp.models.Product
import com.example.productpromotionapp.models.ProductList
import com.example.productpromotionapp.services.DummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class activity_products : AppCompatActivity() {

    lateinit var dummyService: DummyService
    lateinit var txtSearch : EditText
    lateinit var btnSearch : ImageButton
    lateinit var listProducts : ListView
    lateinit var products : MutableList<Product>

    var policy : StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()

    companion object{
        lateinit var selectedProduct : Product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        StrictMode.setThreadPolicy(policy)

        dummyService = ApiClient.getClient().create(DummyService::class.java)
        txtSearch = findViewById(R.id.txtSearch)
        btnSearch = findViewById(R.id.btnSearch)
        listProducts = findViewById(R.id.listProducts)
        products = mutableListOf()

        val productsAdapter = ProductsAdapter(this@activity_products, products)
        listProducts.adapter = productsAdapter

        dummyService.getFirstTen(10).enqueue(object : Callback<ProductList>{
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>){
                if(response.body() != null){
                    products.clear()
                    (response.body()!!.plist as Collection<Product>?)?.let { products.addAll(it) }
                    //products = (response.body()!!.plist as MutableList<Product>?)!!
                    //var productsAdapter = ProductsAdapter(this@activity_products, products)
                    //listProducts.adapter = productsAdapter
                    productsAdapter.notifyDataSetChanged()

                }
            }
            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.e("error : " , t.toString())
                Toast.makeText(this@activity_products, "Failed to fetch products", Toast.LENGTH_LONG).show()

            }
        })

        listProducts.setOnItemClickListener { parent, view, position, id ->
            selectedProduct = products.get(id.toInt())
            var intent = Intent(this, activity_details::class.java)
            startActivity(intent)
        }

        btnSearch.setOnClickListener {
            if(txtSearch.text.toString() != ""){
                dummyService.getSearched(txtSearch.text.toString()).enqueue(object : Callback<ProductList>{
                    override fun onResponse(call: Call<ProductList>, response: Response<ProductList>){
                        if(response.body() != null){
                            products = (response.body()!!.plist as MutableList<Product>?)!!
                            productsAdapter.updateList(products)
                        }
                    }
                    override fun onFailure(call: Call<ProductList>, t: Throwable) {
                        Log.e("error : " , t.toString())
                        Toast.makeText(this@activity_products, "Failed to fetch products", Toast.LENGTH_LONG).show()

                    }
                })
            }
        }

    }
}