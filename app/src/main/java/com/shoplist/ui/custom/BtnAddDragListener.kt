package com.shoplist.ui.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.DragEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class BtnAddDragListener(
    private val fabButton: FloatingActionButton,
    val context: Context,
    private val listener: Listener
) : View.OnDragListener {

    private var trashOpen: Drawable? =
        ContextCompat.getDrawable(context, com.shoplist.R.drawable.delete_forever_24)
    private var trashClose: Drawable? =
        ContextCompat.getDrawable(context, com.shoplist.R.drawable.delete_24)
    private var iconDefault: Drawable? =
        ContextCompat.getDrawable(context, com.shoplist.R.mipmap.add_to_basket)
    private var wasDropped = false

    override fun onDrag(v: View?, event: DragEvent?): Boolean {

        when (event!!.action) {
            DragEvent.ACTION_DRAG_ENTERED -> {
                fabButton.setImageDrawable(trashOpen)
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                fabButton.setImageDrawable(trashClose)
            }
            DragEvent.ACTION_DROP -> {
                fabButton.setImageDrawable(iconDefault)
                wasDropped = true
                listener.onShopItemDropped()
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                if (!wasDropped) {
                    v?.visibility = View.VISIBLE
                }
                fabButton.setImageDrawable(iconDefault)
                wasDropped = false
            }
        }
        return true
    }

    interface Listener {
        fun onShopItemDropped()
    }
}