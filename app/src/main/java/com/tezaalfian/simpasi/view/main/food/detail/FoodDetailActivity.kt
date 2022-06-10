package com.tezaalfian.simpasi.view.main.food.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.source.local.entity.FoodEntity
import com.tezaalfian.simpasi.databinding.ActivityFoodDetailBinding

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val food = intent.getParcelableExtra<FoodEntity>(EXTRA_FOOD) as FoodEntity

        binding.apply {
            tvFoodName.text = food.resep
            tvDescription.text = food.bahan
        }
        Glide.with(this)
            .load(R.drawable.food)
            .centerCrop()
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_error)
            .into(binding.imgFoodMenu)
    }

    companion object {
        const val EXTRA_FOOD = "extra_food"
    }
}