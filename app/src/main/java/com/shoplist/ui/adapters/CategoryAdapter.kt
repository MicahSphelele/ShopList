package com.shoplist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shoplist.R
import com.shoplist.models.Category

class CategoryAdapter(private val list:List<Category>, private val listener:CategoryListener) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]

        holder.run {
            categoryName.text = category.catName
            categoryImage.setImageDrawable(ContextCompat.getDrawable(itemView.context,category.catImage))
            itemView.setOnClickListener {
                listener.onCategoryClicked(category)
            }
        }

    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        var categoryName : TextView = v.findViewById(R.id.categoryName)
        var categoryImage : ImageView = v.findViewById(R.id.categoryImage)
    }

    interface CategoryListener{
        fun onCategoryClicked(category: Category)
    }
}