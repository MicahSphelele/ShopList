package com.shoplist.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shoplist.R
import com.shoplist.databinding.ItemShopBinding
import com.shoplist.domain.models.ShopItem
import com.shoplist.extensions.returnItemsOrItem
import com.shoplist.extensions.showPopupMenu
import com.shoplist.extensions.viewHolderItemBinding
import com.shoplist.util.Constants

class ShopItemAdapter :
    RecyclerView.Adapter<ShopItemAdapter.ViewHolder>() {

    private lateinit var list: List<ShopItem>
    private lateinit var listener: ShopItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.viewHolderItemBinding(R.layout.item_shop) as ItemShopBinding)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list.isNotEmpty()) {
            val shopItem = list[position]

            holder.viewBinder.shopItem = shopItem

            holder.viewBinder.formattedPrice = Constants.formatCurrency(shopItem.itemCost)

            holder.viewBinder.run {
                itemQuantity.text = shopItem.quantity.returnItemsOrItem()
                itemCheck.isChecked = shopItem.isMarked
                btnMore.setOnClickListener {
                    showPopupMenu(btnMore) { action ->
                        listener.onAction(shopItem, action)
                    }
                }

                //Set Strike through according to OnCheckedChangeListener
                itemCheck.setOnCheckedChangeListener { _, isChecked ->
                    shopItem.isMarked = isChecked
                    listener.onShopItemMarked(shopItem)
                }
            }
        }
    }

    fun setShopItemList(list: List<ShopItem>) {
        this.list = list
        this.asyncListDiffer.submitList(this.list)
    }

    fun setListener(listener: ShopItemListener) {
        this.listener = listener
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<ShopItem>() {
        override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffCallBack)

    class ViewHolder(val viewBinder: ItemShopBinding) : RecyclerView.ViewHolder(viewBinder.root)

    interface ShopItemListener {
        fun onShopItemMarked(shopItem: ShopItem)
        fun onAction(shopItem: ShopItem, action: ShopItemAction)
    }

    enum class ShopItemAction {
        EDIT, DELETE
    }
}