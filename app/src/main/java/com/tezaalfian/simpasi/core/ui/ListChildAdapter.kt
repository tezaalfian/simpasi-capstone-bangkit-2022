package com.tezaalfian.simpasi.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.domain.model.Child
import com.tezaalfian.simpasi.databinding.ItemChildrenBinding
import java.util.ArrayList

class ListChildAdapter : RecyclerView.Adapter<ListChildAdapter.ListViewHolder>() {

    private var listData = ArrayList<Child>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setData(newListData: List<Child>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(var binding: ItemChildrenBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemChildrenBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val child = listData[position]
        holder.binding.apply {
            tvChildName.text = child.nama
            tvGender.text = child.jkBayi
            tvTglLahir.text = child.tglLahir
        }
        Glide.with(holder.itemView.context)
            .load(R.drawable.baby)
            .centerCrop()
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_error)
            .into(holder.binding.imgChild)
    }

    override fun getItemCount() = listData.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Child)
    }
}