package com.tezaalfian.simpasi.core.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.databinding.ItemChildrenBinding
import java.util.ArrayList

class ListChildAdapter : RecyclerView.Adapter<ListChildAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: ListChildAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private var listData = ArrayList<ChildEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<ChildEntity>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(var binding: ItemChildrenBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, child: ChildEntity) {
            binding.apply {
                tvChildName.text = child.nama
                tvGender.text = when(child.jkBayi) {
                    "L" -> "Laki-laki /"
                    "P" -> "Perempuan /"
                    else -> ""
                }
                tvBb.text = context.resources.getString(R.string.berat, child.bbBayi)
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(child)
                }
            }
            Glide.with(itemView.context)
                .load(R.drawable.baby)
                .centerCrop()
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_error)
                .into(binding.imgChild)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemChildrenBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val child = listData[position]
        holder.bind(holder.itemView.context, child)
    }

    override fun getItemCount() = listData.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ChildEntity)
    }
}