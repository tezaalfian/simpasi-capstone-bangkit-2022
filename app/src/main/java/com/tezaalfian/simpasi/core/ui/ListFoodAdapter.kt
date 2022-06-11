package com.tezaalfian.simpasi.core.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.model.Food
import com.tezaalfian.simpasi.databinding.ItemFoodBinding
import com.tezaalfian.simpasi.view.main.food.FoodDetailActivity
import java.util.ArrayList

class ListFoodAdapter : RecyclerView.Adapter<ListFoodAdapter.ListViewHolder>() {

    private var listData = ArrayList<Food>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<Food>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(var binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, food: Food) {
            binding.apply {
                tvFoodName.text = food.resep
                tvDescription.text = food.bahan
                itemView.setOnClickListener {
                    val intent = Intent(context, FoodDetailActivity::class.java)
                    intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food)
                    context.startActivity(intent)
                }
            }
            Glide.with(itemView.context)
                .load(R.drawable.food)
                .centerCrop()
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_error)
                .into(binding.imgFoodMenu)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val child = listData[position]
        holder.bind(holder.itemView.context, child)
    }

    override fun getItemCount() = listData.size
}