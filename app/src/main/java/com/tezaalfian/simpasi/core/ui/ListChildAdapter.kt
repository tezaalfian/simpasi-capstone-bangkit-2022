package com.tezaalfian.simpasi.core.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.core.utils.setLocalDateFormat
import com.tezaalfian.simpasi.databinding.ItemChildrenBinding
import com.tezaalfian.simpasi.view.main.children.AddEditChildActivity
import java.util.ArrayList

class ListChildAdapter : RecyclerView.Adapter<ListChildAdapter.ListViewHolder>() {

    private var listData = ArrayList<ChildEntity>()

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
                tvGender.text = "${child.jkBayi} /"
                tvTglLahir.setLocalDateFormat(child.tglLahir)
                itemView.setOnClickListener {
                    val intent = Intent(context, AddEditChildActivity::class.java)
                    intent.putExtra(AddEditChildActivity.STATE, "edit")
                    intent.putExtra(AddEditChildActivity.EXTRA_CHILD, child)
                    context.startActivity(intent)
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
}