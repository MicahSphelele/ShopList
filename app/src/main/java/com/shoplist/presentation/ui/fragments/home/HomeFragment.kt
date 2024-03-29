package com.shoplist.presentation.ui.fragments.home

import android.content.ClipData
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.DragShadowBuilder
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import com.shoplist.R
import com.shoplist.databinding.FragmentHomeBinding
import com.shoplist.domain.models.ShopItem
import com.shoplist.util.extensions.hideShowImageAndText
import com.shoplist.util.extensions.returnItemsOrItem
import com.shoplist.presentation.ui.adapters.ShopItemAdapter
import com.shoplist.presentation.ui.custom.BtnAddDragListener
import com.shoplist.presentation.ui.custom.RecyclerViewItemClickListener
import com.shoplist.presentation.ui.fragments.addoredit.AddShopItemFragment
import com.shoplist.util.AppLogger
import com.shoplist.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ShopItemAdapter.ShopItemListener,
    BtnAddDragListener.Listener {

    private val viewModel by viewModels<HomeViewModel>()

    private var shopItems: List<ShopItem>? = null
    private var selectedShopItem: ShopItem? = null

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

                                selectedShopItem = shopItems!![position]
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
        viewModel.getAllShopItems(viewLifecycleOwner) {
            shopItems = it
            if (shopItems?.isNotEmpty()!!) {
                binding.recyclerView.run {
                    shopItemAdapter.setShopItemList(shopItems!!)
                    adapter = shopItemAdapter
                }
                return@getAllShopItems
            }
            binding.recyclerView.run {
                adapter = shopItemAdapter
                shopItemAdapter.setShopItemList(it!!)
            }
            binding.hideShowImageAndText(View.VISIBLE)
        }

        viewModel.getTotalEstimationCost(viewLifecycleOwner) {
            if (it != null) {
                binding.txtCostEstimation.text = Constants.formatCurrency(it)
                return@getTotalEstimationCost
            }
            binding.txtCostEstimation.text = Constants.formatCurrency(0.00)
        }

        viewModel.getTotalMarkedItems(viewLifecycleOwner) {
            binding.txtTotalMarked.text = it.returnItemsOrItem()
        }
    }

    override fun onShopItemDropped() {
        viewModel.deleteShopItem(selectedShopItem!!)
    }

    override fun onShopItemMarked(shopItem: ShopItem) {
        viewModel.updatedShopItem(shopItem) {
            AppLogger.info("ShopItem: ${shopItem.name} updated")
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
        viewModel.deleteShopItem(shopItem)
    }

}