package com.shoplist.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialElevationScale
import com.shoplist.R
import com.shoplist.models.Category
import com.shoplist.models.ShopItem
import com.shoplist.models.ShopItemParcelable
import com.shoplist.mvvm.viewmodels.CategoryViewModel
import com.shoplist.mvvm.viewmodels.ShopItemViewModel
import com.shoplist.ui.adapters.CategoryAdapter
import com.shoplist.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_shop_item.*
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class AddShopItemFragment : Fragment(), CategoryAdapter.CategoryListener {

    companion object {
        const val ACTION = "action"
        const val PARCELABLE = "parcelableShopItem"
    }

    private val categoryViewModel by viewModels<CategoryViewModel>()
    private val shopItemViewModel by viewModels<ShopItemViewModel>()

    private lateinit var action: String
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var category: Category
    private var shopItemId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Calling setRetainInstance(true) in a Fragment’s onCreate method will keep a fragment instance across configuration
        // changes (instead of destroying and recreating it).
        retainInstance = true
        enterTransition = MaterialElevationScale(/* growing= */ true)
        //exitTransition = MaterialElevationScale(/* growing= */ false)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        action = requireArguments().getString(ACTION, "")

        setDataAccordingToAction(action)

        btnBack.setOnClickListener {
            findNavController().navigateUp()
            hideKeyBoard()
        }

        btnSpinner.setOnClickListener {
            bottomSheetDialog = categoryBottomDialog()
            bottomSheetDialog.show()
        }

        btnAddItem.setOnClickListener {
            insertOrUpdate()
        }

    }

    override fun onStart() {
        super.onStart()
        categoryViewModel.getAllCategories()?.observe(viewLifecycleOwner, {
            categoryAdapter = CategoryAdapter(it, this)
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
    private fun categoryBottomDialog(): BottomSheetDialog {
        val bottomDialog = BottomSheetDialog(requireContext())
        bottomDialog.dismissWithAnimation = true
        bottomDialog.setCancelable(true)
        bottomDialog.dismissWithAnimation = true
        val v = LayoutInflater.from(context).inflate(R.layout.bottom_dialog_categories, null)
        val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = categoryAdapter
        bottomDialog.setContentView(v)

        return bottomDialog
    }

    private fun hideKeyBoard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun setDataAccordingToAction(act: String) {
        if (act == Constants.ACTION_EDIT_VAL) {
            txtTitle.text = requireActivity().getText(R.string._edit_item)
            btnAddItem.text = requireActivity().getText(R.string.edit_item)
            val shopItem: ShopItemParcelable? = requireArguments().getParcelable(PARCELABLE)

            if (shopItem?.id != null) {
                shopItemId = shopItem.id
            }

            editItemName.setText(shopItem?.name)
            editItemCost.setText(shopItem?.itemCost.toString())

            if (shopItem?.categoryId != null) {

                lifecycleScope.launch {
                    if (categoryViewModel.getCategoryById(shopItem.categoryId) != null) {
                        category = categoryViewModel.getCategoryById(shopItem.categoryId)!!
                        btnSpinner.text = category.catName
                    }
                }
            }

            numberPicker.number = shopItem?.quantity.toString()
        }
    }

    private fun insertOrUpdate() {
        val itemName = editItemName.text.toString()
        var itemQuantity = numberPicker.number.toInt()

        if (itemName.isEmpty()) {
            editItemName.error = "Item name is required"
            return
        }
        if (editItemCost.text.toString().isEmpty()) {
            editItemCost.error = "Item cost is required"
            return
        }
        if (btnSpinner.text.toString().equals(getString(R.string.item_category), true)) {
            btnSpinner.error = "Item category is required"
            return
        }
        if (itemQuantity == 0) {
            itemQuantity = 1
        }
        val categoryId = category.id
        val itemCost = editItemCost.text.toString().toDouble()

        if (action == Constants.ACTION_ADD_VAL) {
            lifecycleScope.launch {
                shopItemViewModel.insert(
                    ShopItem(
                        itemName,
                        Constants.getCurrentDateTime(),
                        itemQuantity,
                        categoryId,
                        itemCost
                    )
                )
            }

            Toast.makeText(context, "Item added successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
            return
        }

        val shopItem =
            ShopItem(itemName, Constants.getCurrentDateTime(), itemQuantity, categoryId, itemCost)
        shopItem.id = shopItemId
        lifecycleScope.launch {
            shopItemViewModel.update(shopItem)
        }
        Toast.makeText(context, "Item Updated successfully!", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()


    }
}