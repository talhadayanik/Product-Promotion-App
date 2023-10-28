package com.example.productpromotionapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.productpromotionapp.configs.ApiClient
import com.example.productpromotionapp.configs.Util
import com.example.productpromotionapp.models.JWTData
import com.example.productpromotionapp.models.JWTUser
import com.example.productpromotionapp.services.DummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var txtUsername : EditText
    lateinit var txtPassword : EditText
    lateinit var btnLogin : Button

    lateinit var dummyService : DummyService
    lateinit var sharedPreferences : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("users", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        dummyService = ApiClient.getClient().create(DummyService::class.java)

        txtUsername = findViewById(R.id.txtUsername)
        txtPassword = findViewById(R.id.txtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener(btnLoginOnClickListener)

        val username = sharedPreferences.getString("username", "")
        txtUsername.setText(username)
    }

    val btnLoginOnClickListener = View.OnClickListener {
        val username = txtUsername.text.toString()
        val password = txtPassword.text.toString()

        if (username != "" && password != ""){
            val JWTUser = JWTUser(username, password)
            dummyService.login(JWTUser).enqueue(object : Callback<JWTData>{
                override fun onResponse(call: Call<JWTData>, response: Response<JWTData>) {
                    val JWTData = response.body()
                    if(JWTData != null){
                        Util.user = JWTData

                        editor.putString("username", JWTData.username)
                        editor.commit()

                        val intent = Intent(this@MainActivity, activity_products::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                override fun onFailure(call: Call<JWTData>, t: Throwable) {
                    println(t.toString())
                    Toast.makeText(this@MainActivity, "Cannot connect to server", Toast.LENGTH_LONG).show()
                }
            })
        }else{
            Toast.makeText(this@MainActivity, "Username and password cant be empty", Toast.LENGTH_SHORT).show()
        }

    }
}