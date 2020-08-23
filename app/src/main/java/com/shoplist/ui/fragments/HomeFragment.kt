package com.shoplist.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import com.shoplist.R
import com.shoplist.models.ShopItem
import com.shoplist.models.ShopItemParcelable
import com.shoplist.mvvm.viewmodels.ShopItemViewModel
import com.shoplist.ui.adapters.ShopItemAdapter
import com.shoplist.util.Constants
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), ShopItemAdapter.ShopItemListener {

    private lateinit var shopItemAdapter: ShopItemAdapter
    private lateinit var shopItemViewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialElevationScale(/* growing= */ true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("@GET","IMAGE : ${R.mipmap.electronics}")
        shopItemViewModel = ViewModelProvider(requireActivity()).get(ShopItemViewModel::class.java)
        shopItemViewModel.init(requireActivity().application)

        shopItemAdapter = ShopItemAdapter(this@HomeFragment)

        hideShowImageAndText(true)

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        recyclerView?.let {
            recyclerView?.apply {
                layoutManager = linearLayoutManager
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        if(linearLayoutManager.findLastVisibleItemPosition() == linearLayoutManager.itemCount-1){
                            if(linearLayoutManager.itemCount>=15){
                                btnAdd.visibility = View.GONE
                            }
                        }else{
                            btnAdd.visibility = View.VISIBLE
                        }
                    }
                })
            }
        }
        
        btnAdd.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(AddShopItemFragment.ACTION,Constants.ACTION_ADD_VAL)
           findNavController().navigate(R.id.add_shop_item_fragment,bundle,null,null)
        }
    }

    override fun onStart() {
        super.onStart()
        shopItemViewModel.getAllShoppingItems()?.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                recyclerView.run {
                    shopItemAdapter.setList(it)
                    adapter =  shopItemAdapter
                }
                return@Observer
            }
            recyclerView.run {
                shopItemAdapter.setList(it)
                adapter =  shopItemAdapter
            }
            hideShowImageAndText(false)
        })
    }

    override fun onShopItemMarked(shopItem: ShopItem,isMarked:Boolean) {
        shopItem.isMarked = isMarked
        shopItemViewModel.update(shopItem)
    }

    override fun onAction(shopItem: ShopItem, action: ShopItemAdapter.ShopItemAction) {
        if(action==ShopItemAdapter.ShopItemAction.EDIT){
            val bundle = Bundle()
            bundle.putString(AddShopItemFragment.ACTION,Constants.ACTION_EDIT_VAL)
            bundle.putParcelable(AddShopItemFragment.PARCELABLE,ShopItemParcelable(shopItem.id,shopItem.name,
                shopItem.dateAdded,shopItem.quantity,shopItem.categoryId,shopItem.itemCost,shopItem.isMarked))
            findNavController().navigate(R.id.add_shop_item_fragment,bundle,null,null)
            return
        }
        shopItemViewModel.delete(shopItem)

    }

    private fun hideShowImageAndText(hide:Boolean){
        if(hide){
            image.visibility = View.GONE
            text.visibility = View.GONE
        }else{
            image.visibility = View.VISIBLE
            text.visibility = View.VISIBLE
        }

    }

}