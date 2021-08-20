package com.shoplist.ui.fragments

import android.content.ClipData
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.DragShadowBuilder
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import com.shoplist.R
import com.shoplist.models.ShopItem
import com.shoplist.models.ShopItemParcelable
import com.shoplist.mvvm.viewmodels.ShopItemViewModel
import com.shoplist.ui.adapters.ShopItemAdapter
import com.shoplist.ui.custom.BtnAddDragListener
import com.shoplist.ui.custom.RecyclerViewItemClickListener
import com.shoplist.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ShopItemAdapter.ShopItemListener, BtnAddDragListener.Listener {

    private val shopItemViewModel by viewModels<ShopItemViewModel>()

    private lateinit var shopItems: List<ShopItem>
    private lateinit var selectedShopItem: ShopItem

    @Inject
    lateinit var shopItemAdapter: ShopItemAdapter

    private var isBtnAddHidden = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Calling setRetainInstance(true) in a Fragment’s onCreate method will keep a fragment instance across configuration
        // changes (instead of destroying and recreating it).
        retainInstance = true
        enterTransition = MaterialElevationScale(/* growing= */ true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shopItemAdapter.setListener(this)

        hideShowImageAndText(true)

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        recyclerView?.let {
            recyclerView?.apply {
                layoutManager = linearLayoutManager
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                        if (dy > 0) {

                            if (btnAdd.isShown) {
                                btnAdd.hide()
                                isBtnAddHidden = true
                            }

                        } else if (dy < 0) {

                            if (!btnAdd.isShown) {
                                btnAdd.show()
                                isBtnAddHidden = true
                            }
                        }
                    }
                })

                addOnItemTouchListener(
                    RecyclerViewItemClickListener(
                        context,
                        this,
                        object : RecyclerViewItemClickListener.ClickListener {

                            override fun onClick(view: View?, position: Int) {

                            }

                            override fun onLongClick(view: View?, position: Int) {

                                selectedShopItem = shopItems[position]
                                val clipData = ClipData.newPlainText("", "")
                                val shadowBuilder = DragShadowBuilder(view)

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                    view?.startDragAndDrop(clipData, shadowBuilder, 0, 0)

                                } else {

                                    @Suppress("DEPRECATION")
                                    view!!.startDrag(clipData, shadowBuilder, view, 0)
                                }

                                view?.visibility = View.VISIBLE

                                if (isBtnAddHidden) {
                                    btnAdd.show()
                                }

                                btnAdd.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        context,
                                        R.drawable.delete_24
                                    )
                                )

                            }

                        })
                )
            }
        }

        btnAdd.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(AddShopItemFragment.ACTION, Constants.ACTION_ADD_VAL)
            findNavController().navigate(R.id.add_shop_item_fragment, bundle, null, null)
        }

        btnAdd.setOnDragListener(BtnAddDragListener(btnAdd, requireContext(), this))
    }


    override fun onStart() {
        super.onStart()
        shopItemViewModel.getAllShoppingItems()?.observe(viewLifecycleOwner, Observer {
            shopItems = it
            if (shopItems.isNotEmpty()) {
                recyclerView.run {
                    shopItemAdapter.setShopItemList(shopItems)
                    adapter = shopItemAdapter
                }
                return@Observer
            }
            recyclerView.run {
                adapter = shopItemAdapter
                shopItemAdapter.setShopItemList(it)
            }
            hideShowImageAndText(false)
        })

        shopItemViewModel.getTotalEstimationCost()?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                txtCostEstimation.text = Constants.formatCurrency(it)
                return@Observer
            }
            txtCostEstimation.text = Constants.formatCurrency(0.00)
        })

        shopItemViewModel.getTotalMarkedItems()?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it > 1) {
                    txtTotalMarked.text =
                        String.format("%s ✔ ${Constants.returnItemsOrItem(it)}", it)
                } else {
                    txtTotalMarked.text =
                        String.format("%s ✔ ${Constants.returnItemsOrItem(it)}", it)
                }

                return@Observer
            }
            txtTotalMarked.text = String.format("%s ✔ ${Constants.returnItemsOrItem(0)}", 0)
        })
    }

    override fun onShopItemDropped() {
        lifecycleScope.launch {
            shopItemViewModel.delete(selectedShopItem)
        }
    }

    override fun onShopItemMarked(shopItem: ShopItem) {
        lifecycleScope.launch {
            shopItemViewModel.update(shopItem)
        }
    }

    override fun onAction(shopItem: ShopItem, action: ShopItemAdapter.ShopItemAction) {
        if (action == ShopItemAdapter.ShopItemAction.EDIT) {

            val bundle = Bundle()
            bundle.putString(AddShopItemFragment.ACTION, Constants.ACTION_EDIT_VAL)
            bundle.putParcelable(
                AddShopItemFragment.PARCELABLE, ShopItemParcelable(
                    shopItem.id,
                    shopItem.name,
                    shopItem.dateAdded,
                    shopItem.quantity,
                    shopItem.categoryId,
                    shopItem.itemCost,
                    shopItem.isMarked
                )
            )
            findNavController().navigate(R.id.add_shop_item_fragment, bundle, null, null)
            return
        }
        lifecycleScope.launch {
            shopItemViewModel.delete(shopItem)
        }
    }

    private fun hideShowImageAndText(hide: Boolean) {
        if (hide) {
            image.visibility = View.GONE
            text.visibility = View.GONE
        } else {
            image.visibility = View.VISIBLE
            text.visibility = View.VISIBLE
        }

    }
}