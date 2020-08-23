package com.shoplist.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialElevationScale
import com.shoplist.R
import com.shoplist.models.Category
import com.shoplist.models.ShopItem
import com.shoplist.mvvm.viewmodels.CategoryViewModel
import com.shoplist.mvvm.viewmodels.ShopItemViewModel
import com.shoplist.ui.adapters.CategoryAdapter
import com.shoplist.util.Constants
import kotlinx.android.synthetic.main.fragment_add_shop_item.*
import kotlinx.android.synthetic.main.fragment_home.*

class AddShopItemFragment : Fragment(), CategoryAdapter.CategoryListener {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(/* growing= */ false)
        enterTransition = MaterialElevationScale(/* growing= */ true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_add_shop_item, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel = ViewModelProvider(requireActivity()).get(CategoryViewModel::class.java)
        categoryViewModel.init(requireActivity().application)

        val shopItemViewModel = ViewModelProvider(requireActivity()).get(ShopItemViewModel::class.java)
        shopItemViewModel.init(requireActivity().application)

        btnBack.setOnClickListener {
            findNavController().navigateUp()
            hideKeyBoard()
        }

        btnSpinner.setOnClickListener {
            bottomSheetDialog = categoryBottomDialog()
            bottomSheetDialog.show()
        }

        btnAddItem.setOnClickListener{
          val insert= shopItemViewModel.insert(ShopItem(editItemName.text.toString(),
              Constants.getCurrentDateTime(),
              numberPicker.number.toInt(),category.id,editItemCost.text.toString().toDouble()))
        }

    }

    override fun onStart() {
        super.onStart()
        categoryViewModel.getAllCategories()?.observe(viewLifecycleOwner, Observer {
            categoryAdapter = CategoryAdapter(it,this)
        })
    }

    override fun onPause() {
        super.onPause()
        hideKeyBoard()
    }

    override fun onCategoryClicked(category: Category) {
        this.category = category
        bottomSheetDialog.dismiss()
        btnSpinner.text = category.catName
    }

    @SuppressLint("InflateParams")
    private fun categoryBottomDialog() : BottomSheetDialog {
        val bottomDialog = BottomSheetDialog(requireContext())
        bottomDialog.dismissWithAnimation = true
        bottomDialog.setCancelable(true)
        bottomDialog.dismissWithAnimation = true
        val v = LayoutInflater.from(context).inflate(R.layout.bottom_dialog_categories,null)
        val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = categoryAdapter
        bottomDialog.setContentView(v)

        return bottomDialog
    }

    private fun hideKeyBoard(){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}