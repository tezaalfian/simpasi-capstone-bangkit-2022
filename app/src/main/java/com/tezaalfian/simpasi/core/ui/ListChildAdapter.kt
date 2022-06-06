package com.tezaalfian.simpasi.core.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tezaalfian.simpasi.R
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity
import com.tezaalfian.simpasi.databinding.ItemChildrenBinding
import com.tezaalfian.simpasi.view.main.children.AddEditChildActivity
import com.tezaalfian.simpasi.view.main.children.BahanActivity
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
                tvGender.text = when(child.jkBayi) {
                    "L" -> "Laki-laki /"
                    "P" -> "Perempuan /"
                    else -> ""
                }
                tvBb.text = "${child.bbBayi} kg"
                itemView.setOnClickListener {
                    MaterialAlertDialogBuilder(context)
                        .setTitle(context.resources.getString(R.string.title_children))
                        .setMessage("Pilih Aksi")
                        .setNegativeButton("Feedback Bahan") { dialog, which ->
                            val intent = Intent(context, BahanActivity::class.java)
                            intent.putExtra(BahanActivity.EXTRA_CHILD, child)
                            context.startActivity(intent)
                        }
                        .setPositiveButton("Edit") { dialog, which ->
                            val intent = Intent(context, AddEditChildActivity::class.java)
                            intent.putExtra(AddEditChildActivity.STATE, "edit")
                            intent.putExtra(AddEditChildActivity.EXTRA_CHILD, child)
                            context.startActivity(intent)
                        }
                        .show()
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