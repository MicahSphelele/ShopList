package com.shoplist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shoplist.R
import com.shoplist.models.Category

class CategoryAdapter :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var list: List<Category> = mutableListOf()
    private lateinit var listener: CategoryListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]

        holder.run {
            categoryName.text = category.catName
            categoryImage.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    category.catImage
                )
            )
            itemView.setOnClickListener {
                listener.onCategoryClicked(category)
            }
        }
    }

    fun setCategories(list: List<Category>?) {
        this.list = list!!
        this.asyncListDiffer.submitList(this.list)
    }

    fun setListener(listener: CategoryListener) {
        this.listener = listener
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.catName == newItem.catName
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffCallBack)

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var categoryName: TextView = v.findViewById(R.id.categoryName)
        var categoryImage: ImageView = v.findViewById(R.id.categoryImage)
    }

    interface CategoryListener {
        fun onCategoryClicked(category: Category)
    }
}