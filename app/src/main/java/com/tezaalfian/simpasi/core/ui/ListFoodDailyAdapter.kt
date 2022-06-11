package com.tezaalfian.simpasi.core.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.source.local.entity.FoodEntity
import com.tezaalfian.simpasi.databinding.ItemFoodBinding
import com.tezaalfian.simpasi.databinding.ItemFoodEditableBinding
import com.tezaalfian.simpasi.view.main.food.FoodDetailActivity
import java.util.ArrayList

class ListFoodDailyAdapter : RecyclerView.Adapter<ListFoodDailyAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    private var listData = ArrayList<FoodEntity>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(newListData: List<FoodEntity>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(var binding: ItemFoodEditableBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, food: FoodEntity) {
            binding.apply {
                tvFoodName.text = food.resep
                tvDescription.text = food.bahan
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(food)
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
        val binding = ItemFoodEditableBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val child = listData[position]
        holder.bind(holder.itemView.context, child)
    }

    override fun getItemCount() = listData.size

    interface OnItemClickCallback {
        fun onItemClicked(data: FoodEntity)
    }
}