package com.shoplist.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialElevationScale
import com.shoplist.R
import com.shoplist.databinding.BottomDialogCategoriesBinding
import com.shoplist.databinding.FragmentAddShopItemBinding
import com.shoplist.domain.models.Category
import com.shoplist.domain.models.ShopItem
import com.shoplist.domain.models.ShopItemParcelable
import com.shoplist.viewmodels.CategoryViewModel
import com.shoplist.mvvm.viewmodels.ShopItemViewModel
import com.shoplist.ui.adapters.CategoryAdapter
import com.shoplist.util.Constants
import com.shoplist.util.getViewBinder
import com.shoplist.util.hideDeviceSoftKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class AddShopItemFragment : Fragment(R.layout.fragment_add_shop_item), CategoryAdapter.CategoryListener {

    companion object {
        const val ACTION = "action"
        const val PARCELABLE = "parcelableShopItem"
    }

    private val categoryViewModel by viewModels<CategoryViewModel>()
    private val shopItemViewModel by viewModels<ShopItemViewModel>()
    private lateinit var binding: FragmentAddShopItemBinding
    private lateinit var action: String
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var category: Category
    private var shopItemId by Delegates.notNull<Int>()

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Calling setRetainInstance(true) in a Fragment’s onCreate method will keep a fragment instance across configuration
        // changes (instead of destroying and recreating it).
        enterTransition = MaterialElevationScale(/* growing= */ true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action = requireArguments().getString(ACTION, "")

        binding = FragmentAddShopItemBinding.bind(view)

        setDataAccordingToAction(action)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
            requireContext().hideDeviceSoftKeyboard(requireView())
        }

        binding.btnSpinner.setOnClickListener {
            bottomSheetDialog = categoryBottomDialog()
            bottomSheetDialog.show()
        }

        binding.btnAddItem.setOnClickListener {
            insertOrUpdate()
        }

    }

    override fun onStart() {
        super.onStart()
        categoryViewModel.getAllCategories()?.observe(viewLifecycleOwner, {
            categoryAdapter.setCategories(it)
            categoryAdapter.setListener(this)
        })
    }

    override fun onPause() {
        super.onPause()
        requireContext().hideDeviceSoftKeyboard(requireView())
    }

    override fun onCategoryClick(category: Category) {
        this.category = category
        bottomSheetDialog.dismiss()
        binding.btnSpinner.text = category.categoryName
    }

    @SuppressLint("InflateParams")
    private fun categoryBottomDialog(): BottomSheetDialog {
        val bottomDialog = BottomSheetDialog(requireContext())
        bottomDialog.dismissWithAnimation = true
        bottomDialog.setCancelable(true)
        bottomDialog.dismissWithAnimation = true
        val dialogBinder = requireContext().getViewBinder(R.layout.bottom_dialog_categories) as BottomDialogCategoriesBinding
        dialogBinder.recyclerView.layoutManager = LinearLayoutManager(context)
        dialogBinder.recyclerView.adapter = categoryAdapter
        bottomDialog.setContentView(dialogBinder.root)
        return bottomDialog
    }

    private fun setDataAccordingToAction(act: String) {
        if (act == Constants.ACTION_EDIT_VAL) {
            binding.txtTitle.text = requireActivity().getText(R.string._edit_item)
            binding.btnAddItem.text = requireActivity().getText(R.string.edit_item)
            val shopItem: ShopItemParcelable? = requireArguments().getParcelable(PARCELABLE)

            if (shopItem?.id != null) {
                shopItemId = shopItem.id
            }

            binding.editItemName.setText(shopItem?.name)
            binding.editItemCost.setText(shopItem?.itemCost.toString())

            if (shopItem?.categoryId != null) {

                lifecycleScope.launch {
                    if (categoryViewModel.getCategoryById(shopItem.categoryId) != null) {
                        category = categoryViewModel.getCategoryById(shopItem.categoryId)!!
                        binding.btnSpinner.text = category.categoryName
                    }
                }
            }

            binding.numberPicker.number = shopItem?.quantity.toString()
        }
    }

    private fun insertOrUpdate() {
        val itemName = binding.editItemName.text.toString()
        var itemQuantity = binding.numberPicker.number.toInt()

        if (itemName.isEmpty()) {
            binding.editItemName.error = "Item name is required"
            return
        }
        if (binding.editItemCost.text.toString().isEmpty()) {
            binding.editItemCost.error = "Item cost is required"
            return
        }
        if (binding.btnSpinner.text.toString().equals(getString(R.string.item_category), true)) {
            binding.btnSpinner.error = "Item category is required"
            return
        }
        if (itemQuantity == 0) {
            itemQuantity = 1
        }
        val categoryId = category.id
        val itemCost = binding.editItemCost.text.toString().toDouble()

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
            Toast.makeText(requireContext(), "Item successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
            return
        }

        val shopItem =
            ShopItem(itemName, Constants.getCurrentDateTime(), itemQuantity, categoryId, itemCost)
        shopItem.id = shopItemId
        lifecycleScope.launch {
            shopItemViewModel.update(shopItem)
        }
        Toast.makeText(requireContext(), "Item updated successfully!", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()


    }
}