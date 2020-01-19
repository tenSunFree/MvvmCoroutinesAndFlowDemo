package com.home.mvvmcoroutinesandflowdemo.detail.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.home.mvvmcoroutinesandflowdemo.common.room.models.DetailEntity

class DetailAdapter :
    ListAdapter<DetailEntity, DetailAdapter.DetailViewHolder>(DetailDiff) {

    private object DetailDiff : DiffUtil.ItemCallback<DetailEntity>() {
        override fun areItemsTheSame(oldItem: DetailEntity, newItem: DetailEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DetailEntity, newItem: DetailEntity) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            LayoutInflater.from(parent.context).inflate(
                com.home.mvvmcoroutinesandflowdemo.R.layout.fragment_detail_item,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idMaterialTextView: MaterialTextView =
            itemView.findViewById(com.home.mvvmcoroutinesandflowdemo.R.id.material_text_view_id)
        private val imageView: ImageView =
            itemView.findViewById(com.home.mvvmcoroutinesandflowdemo.R.id.image_view)

        fun bindData(forecast: DetailEntity?) {
            forecast?.apply {
                idMaterialTextView.text = id.toString()
                var url = img_src
                if (url.startsWith("http://")) {
                    url = url.replace("http://", "https://")
                }
                Glide.with(imageView.context).load(url).into(imageView)
            }
        }
    }
}