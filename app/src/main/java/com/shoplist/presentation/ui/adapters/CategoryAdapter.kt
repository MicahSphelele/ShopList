package com.shoplist.presentation.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shoplist.R
import com.shoplist.databinding.ItemCategoryBinding
import com.shoplist.domain.models.Category
import com.shoplist.extensions.viewHolderItemBinding

class CategoryAdapter :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var list: List<Category> = mutableListOf()
    private lateinit var listener: CategoryListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.viewHolderItemBinding(R.layout.item_category) as ItemCategoryBinding)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val category = list[position]
        holder.viewBinder.category = category
        holder.itemView.setOnClickListener {
            listener.onCategoryClick(category)
        }
    }

    fun setCategories(list: List<Category>?) {
        this.list = list!!
        asyncListDiffer.submitList(this.list)
    }

    fun setListener(listener: CategoryListener) {
        this.listener = listener
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.categoryName == newItem.categoryName
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffCallBack)

    inner class ViewHolder(val viewBinder: ItemCategoryBinding) :
        RecyclerView.ViewHolder(viewBinder.root)

    interface CategoryListener {
        fun onCategoryClick(category: Category)
    }
}