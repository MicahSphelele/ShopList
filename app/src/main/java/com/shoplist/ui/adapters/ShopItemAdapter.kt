package com.shoplist.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.shoplist.R
import com.shoplist.models.ShopItem
import com.shoplist.util.Constants


class ShopItemAdapter(private val listener: ShopItemListener) :
    RecyclerView.Adapter<ShopItemAdapter.ViewHolder>() {

    private lateinit var list: List<ShopItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopItem = list[position]

        holder.run {

            itemName.text = shopItem.name
            itemCost.text = Constants.formatCurrency(shopItem.itemCost)
            itemQuantity.text = String.format(
                "%s ${Constants.returnItemsOrItem(shopItem.quantity)}",
                shopItem.quantity
            )

            btnMore.setOnClickListener {

                val popup = PopupMenu(ContextThemeWrapper(itemView.context,R.style.ItemPopUpMenuStyle), btnMore)
                popup.inflate(R.menu.shop_item_menu)
                popup.setOnMenuItemClickListener {

                    if (it.itemId == R.id.item_edit) {
                        listener.onAction(shopItem, ShopItemAction.EDIT)
                    }
                    if (it.itemId == R.id.item_delete) {
                        listener.onAction(shopItem, ShopItemAction.DELETE)
                    }
                    true
                }

                val menuPopupHelper =
                    MenuPopupHelper(ContextThemeWrapper(itemView.context,R.style.ItemPopUpMenuStyle), popup.menu as MenuBuilder, btnMore)
                menuPopupHelper.setForceShowIcon(true)
                menuPopupHelper.show()
            }

            if (shopItem.isMarked) {
                itemName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                itemCost.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                itemQuantity.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }

            //Set checkbox according to is marked
            itemCheck.isChecked = shopItem.isMarked

            //Set Strike through marked if marked is true
            setStrikeThrough(this, shopItem.isMarked)

            //Set Strike through according to OnCheckedChangeListener
            itemCheck.setOnCheckedChangeListener { _, isChecked ->
                setStrikeThrough(holder, isChecked)
                shopItem.isMarked = isChecked
                listener.onShopItemMarked(shopItem)
            }

        }

    }

    fun setList(_list: List<ShopItem>) {
        list = _list
    }

    private fun setStrikeThrough(holder: ViewHolder, isSet: Boolean) {
        if (isSet) {
            holder.itemName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemCost.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemQuantity.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

     class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var itemName: TextView = v.findViewById(R.id.itemName)
        var itemCost: TextView = v.findViewById(R.id.itemCost)
        var itemQuantity: TextView = v.findViewById(R.id.itemQuantity)
        var itemCheck: CheckBox = v.findViewById(R.id.itemCheck)
        var btnMore: ImageView = v.findViewById(R.id.btnMore)
    }

    interface ShopItemListener {
        fun onShopItemMarked(shopItem: ShopItem)
        fun onAction(shopItem: ShopItem, action: ShopItemAction)
    }

    enum class ShopItemAction {
        EDIT, DELETE
    }
}