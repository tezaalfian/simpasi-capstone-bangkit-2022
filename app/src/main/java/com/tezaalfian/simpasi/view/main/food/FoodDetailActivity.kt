package com.tezaalfian.simpasi.view.main.food

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.model.Food
import com.tezaalfian.simpasi.core.data.source.local.entity.FoodEntity
import com.tezaalfian.simpasi.core.ui.FoodViewModelFactory
import com.tezaalfian.simpasi.databinding.ActivityFoodDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailBinding

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val food = intent.getParcelableExtra<Food>(EXTRA_FOOD) as Food

        val factory = FoodViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[FoodViewModel::class.java]

        var token = ""

        viewModel.getToken().observe(this){
            if (!it.isNullOrEmpty()){
                token = it
            }
        }

        binding.apply {
            tvFoodName.text = food.resep
            tvDescription.text = food.bahan
            btnAddFood.setOnClickListener {
                val sdf = SimpleDateFormat("yyyy/MM/dd")
                val currentDate = sdf.format(Date())
                viewModel.insertFood(
                    FoodEntity(
                        foodId = food.id, resep = food.resep, bahan = food.resep, tanggal = currentDate, token = token
                    )
                )
                Toast.makeText(this@FoodDetailActivity, resources.getString(R.string.success), Toast.LENGTH_SHORT).show()
                finish()
            }
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