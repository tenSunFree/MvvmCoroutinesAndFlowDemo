package com.home.mvvmcoroutinesandflowdemo.menu.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.home.mvvmcoroutinesandflowdemo.R

class MenuAdapter :
    ListAdapter<String, MenuAdapter.MenuViewHolder>(ForecastDiff) {

    var setOnItemClickListener: ((yy:Int, ff:String)->Unit)? = null

    private object ForecastDiff : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_menu_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bindData(getItem(position), position, setOnItemClickListener)
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameMaterialTextView: MaterialTextView = itemView.findViewById(R.id.material_text_view_name)
        private val rootMaterialCardView: MaterialCardView = itemView.findViewById(R.id.material_card_view_root)
        fun bindData(
            name: String?,
            position: Int,
            clickItemListener: ((yy:Int, ff:String) -> Unit)?
        ) {
            name?.apply {
                nameMaterialTextView.text = name
                rootMaterialCardView.setOnClickListener {
                    clickItemListener?.invoke(position, name)
                }
            }
        }
    }
}