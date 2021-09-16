package com.shoplist.presentation.ui.fragments

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
import com.shoplist.databinding.FragmentHomeBinding
import com.shoplist.domain.models.ShopItem
import com.shoplist.extensions.hideShowImageAndText
import com.shoplist.extensions.returnItemsOrItem
import com.shoplist.viewmodels.ShopItemViewModel
import com.shoplist.presentation.ui.adapters.ShopItemAdapter
import com.shoplist.presentation.ui.custom.BtnAddDragListener
import com.shoplist.presentation.ui.custom.RecyclerViewItemClickListener
import com.shoplist.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ShopItemAdapter.ShopItemListener,
    BtnAddDragListener.Listener {

    private val shopItemViewModel by viewModels<ShopItemViewModel>()

    private lateinit var shopItems: List<ShopItem>
    private lateinit var selectedShopItem: ShopItem
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var shopItemAdapter: ShopItemAdapter

    private var isBtnAddHidden = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Calling setRetainInstance(true) in a Fragment’s onCreate method will keep a fragment instance across configuration
        // changes (instead of destroying and recreating it).
        enterTransition = MaterialElevationScale(/* growing= */ true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        shopItemAdapter.setListener(this)

        binding.hideShowImageAndText(View.GONE)

        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.let {
            it.apply {
                layoutManager = linearLayoutManager
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                        if (dy > 0) {
                            if (binding.btnAdd.isShown) {
                                binding.btnAdd.hide()
                                isBtnAddHidden = true
                            }

                        } else if (dy < 0) {

                            if (!binding.btnAdd.isShown) {
                                binding.btnAdd.show()
                                isBtnAddHidden = true
                            }
                        }
                    }
                })

                addOnItemTouchListener(
                    RecyclerViewItemClickListener(
                        requireContext(),
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
                                    binding.btnAdd.show()
                                }

                                binding.btnAdd.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.delete_24
                                    )
                                )
                            }
                        })
                )
            }
        }

        binding.btnAdd.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(AddShopItemFragment.ACTION, Constants.ACTION_ADD_VAL)
            findNavController().navigate(R.id.add_shop_item_fragment, bundle, null, null)
        }

        binding.btnAdd.setOnDragListener(BtnAddDragListener(binding.btnAdd, requireContext(), this))
    }


    override fun onStart() {
        super.onStart()
        shopItemViewModel.getAllShoppingItems()?.observe(viewLifecycleOwner, Observer {
            shopItems = it
            if (shopItems.isNotEmpty()) {
                binding.recyclerView.run {
                    shopItemAdapter.setShopItemList(shopItems)
                    adapter = shopItemAdapter
                }
                return@Observer
            }
            binding.recyclerView.run {
                adapter = shopItemAdapter
                shopItemAdapter.setShopItemList(it)
            }
            binding.hideShowImageAndText(View.VISIBLE)
        })

        shopItemViewModel.getTotalEstimationCost()?.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.txtCostEstimation.text = Constants.formatCurrency(it)
                return@observe
            }
            binding.txtCostEstimation.text = Constants.formatCurrency(0.00)
        })

        shopItemViewModel.getTotalMarkedItems()?.observe(viewLifecycleOwner, {
            binding.txtTotalMarked.text = it.returnItemsOrItem()
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
            val bundle = Bundle().apply {
                putString(AddShopItemFragment.ACTION, Constants.ACTION_EDIT_VAL)
                putParcelable(AddShopItemFragment.PARCELABLE, shopItem)
            }
            findNavController().navigate(R.id.add_shop_item_fragment, bundle, null, null)
            return
        }

        lifecycleScope.launch {
            shopItemViewModel.delete(shopItem)
        }
    }

}