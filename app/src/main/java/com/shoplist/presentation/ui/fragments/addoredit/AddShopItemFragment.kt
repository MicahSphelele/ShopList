package com.shoplist.presentation.ui.fragments.addoredit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialElevationScale
import com.shoplist.R
import com.shoplist.databinding.BottomDialogCategoriesBinding
import com.shoplist.databinding.FragmentAddShopItemBinding
import com.shoplist.domain.models.Category
import com.shoplist.domain.models.ShopItem
import com.shoplist.util.extensions.getViewBinder
import com.shoplist.util.extensions.hideDeviceSoftKeyboard
import com.shoplist.presentation.ui.adapters.CategoryAdapter
import com.shoplist.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class AddShopItemFragment : Fragment(R.layout.fragment_add_shop_item),
    CategoryAdapter.CategoryListener {

    companion object {
        const val ACTION = "action"
        const val PARCELABLE = "parcelableShopItem"
    }

    private val viewModel by viewModels<AddOrEditViewModel>()

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
        viewModel.getAllCategories(viewLifecycleOwner) {
            categoryAdapter.setCategories(it)
            categoryAdapter.setListener(this)
        }
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
        val dialogBinder =
            requireContext().getViewBinder(R.layout.bottom_dialog_categories) as BottomDialogCategoriesBinding
        dialogBinder.recyclerView.layoutManager = LinearLayoutManager(context)
        dialogBinder.recyclerView.adapter = categoryAdapter
        bottomDialog.setContentView(dialogBinder.root)
        return bottomDialog
    }

    private fun setDataAccordingToAction(act: String) {
        if (act == Constants.ACTION_EDIT_VAL) {
            binding.txtTitle.text = requireActivity().getText(R.string._edit_item)
            binding.btnAddItem.text = requireActivity().getText(R.string.edit_item)
            val shopItem: ShopItem? = requireArguments().getParcelable(PARCELABLE)

            shopItem?.id?.let {
                shopItemId = it
            }

            binding.editItemName.setText(shopItem?.name)
            binding.editItemCost.setText(shopItem?.itemCost.toString())

            if (shopItem?.categoryId != null) {
                viewModel.getCategoryById(shopItem.categoryId) { _category ->
                    _category?.let {
                        category = it
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
            viewModel.insertShopItem(
                ShopItem(
                    itemName,
                    Constants.getCurrentDateTime(),
                    itemQuantity,
                    categoryId,
                    itemCost
                )
            )
            Toast.makeText(requireContext(), "Item successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
            return
        }

        val shopItem =
            ShopItem(itemName, Constants.getCurrentDateTime(), itemQuantity, categoryId, itemCost)
        shopItem.id = shopItemId
        viewModel.updatedShopItem(shopItem) {
            Toast.makeText(requireContext(), "Item successfully updated!", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigateUp()
        }
    }
}