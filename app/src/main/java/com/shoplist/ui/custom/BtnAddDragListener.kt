package com.shoplist.ui.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.DragEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class BtnAddDragListener(private val fabButton: FloatingActionButton,val context: Context) : View.OnDragListener{

    var trashOpen: Drawable? = ContextCompat.getDrawable(context, com.shoplist.R.drawable.delete_forever_24)
    var trashClose: Drawable? = ContextCompat.getDrawable(context, com.shoplist.R.drawable.delete_24)
    var iconDefault: Drawable? = ContextCompat.getDrawable(context, com.shoplist.R.mipmap.add_to_basket)
    var wasDropped = false

    override fun onDrag(v: View?, event: DragEvent?): Boolean {

        when(event!!.action){
            DragEvent.ACTION_DRAG_ENTERED -> {
                fabButton.setImageDrawable(trashOpen)
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                fabButton.setImageDrawable(trashClose)
            }
            DragEvent.ACTION_DROP -> {
                fabButton.setImageDrawable(iconDefault)
                Toast.makeText(context,"DROP TO DELETE...",Toast.LENGTH_SHORT).show()
                wasDropped = true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                if (!wasDropped) {
                    v?.visibility = View.VISIBLE;
                }
                fabButton.setImageDrawable(iconDefault)
                wasDropped = false
            }


        }

        return true
    }
}