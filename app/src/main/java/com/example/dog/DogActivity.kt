package com.example.dog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.dog.databinding.ActivityDogBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDogBinding
    private val retrofit: Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://dog.ceo")
        .build()

    private val dogService: DogService = retrofit.create(DogService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        retrieveDogPlease()
    }

    private fun retrieveDogPlease() {
        lifecycleScope.launch {
            try {
                Log.e("MainActivity", "success")

                val url = dogService.getDog().message
                Picasso.with(this@DogActivity)
                    .load(url)
                    .into(binding.textViewId)

                Snackbar.make(
                    binding.root,
                    "Another dog?",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Yes") {
                    retrieveDogPlease()
                }.show()
            } catch (e: Exception) {
                Log.e("MainActivity", "error exception: $e")

                Snackbar.make(
                    binding.root,
                    "Error retrieving dog :(",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Retry") {
                    retrieveDogPlease()
                }.show()
            }
        }
    }
}