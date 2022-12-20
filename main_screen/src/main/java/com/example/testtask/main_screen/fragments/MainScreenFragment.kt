package com.example.testtask.main_screen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.main_screen.FilterCategory
import com.example.testtask.main_screen.R
import com.example.testtask.main_screen.adapters.bestSeller.BestSellerDelegateAdapter
import com.example.testtask.main_screen.adapters.delegateAdapter.CompositeAdapter
import com.example.testtask.main_screen.adapters.hotSales.HotSalesDelegateAdapter
import com.example.testtask.main_screen.adapters.selectCategory.CategoriesDelegateAdapter
import com.example.testtask.main_screen.adapters.selectCategory.CategoryItemTag
import com.example.testtask.main_screen.databinding.FragmentMainBinding
import com.example.testtask.main_screen.network.models.BestSeller
import com.example.testtask.main_screen.network.models.HotSale
import com.example.testtask.main_screen.viewmodels.MainViewModel
import com.example.testtask.navigation.AppScreens
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModel<MainViewModel>()

    private val categoryItemClickListener: (CategoryItemTag) -> Unit = { itemTag ->
        viewModel.changeCategory(itemTag)
    }

    private val bestSellerItemClickListener: (BestSeller) -> Unit = {
        openItemDetails()
    }

    private val hotSalesItemClickListener: (HotSale) -> Unit = {
        openItemDetails()
    }


    private val categoriesDelegateAdapter = CategoriesDelegateAdapter(categoryItemClickListener)

    private val compositeAdapter by lazy {
        CompositeAdapter.Builder()
            .add(categoriesDelegateAdapter)
            .add(HotSalesDelegateAdapter(hotSalesItemClickListener))
            .add(BestSellerDelegateAdapter(bestSellerItemClickListener))
            .build()
    }

    private var brandsFilterItems: List<String> = emptyList()
    private var pricesFilterItems: List<String> = emptyList()
    private var sizesFilterItems: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            ivFilter.setOnClickListener { showFilter() }
        }
    }

    private fun openItemDetails() {
        findNavController().navigate(AppScreens.DetailsScreen.Entry)
    }

    private fun showFilter() {
        viewModel.saveCurrentSelectedItems()
        val filterDialog = createFilterBottomSheetDialog()
        filterDialog.show()
    }

    private fun createFilterBottomSheetDialog(): BottomSheetDialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        with(bottomSheetDialog) {
            setContentView(R.layout.bottom_sheet_dialog_filter)
            findViewById<Spinner>(R.id.spinner_brand)?.setFilterSpinner(FilterCategory.BRAND)
            findViewById<Spinner>(R.id.spinner_price)?.setFilterSpinner(FilterCategory.PRICE)
            findViewById<Spinner>(R.id.spinner_size)?.setFilterSpinner(FilterCategory.SIZE)
            findViewById<TextView>(R.id.btn_done_bs)?.setOnClickListener { dismiss() }
            findViewById<ImageView>(R.id.iv_close_btn_bs)?.setOnClickListener {
                undoFilterChanges()
                dismiss()
            }
        }
        return bottomSheetDialog
    }

    private fun undoFilterChanges() {
        viewModel.restorePreviousSelections()
    }

    private fun Spinner.setFilterSpinner(category: FilterCategory) {
        val items = when (category) {
            FilterCategory.BRAND -> brandsFilterItems
            FilterCategory.PRICE -> pricesFilterItems
            FilterCategory.SIZE -> sizesFilterItems
        }
        adapter = createSpinnerAdapter(items).apply { tag = category }
        onItemSelectedListener = createFilterSpinnerOnItemSelectedListener()
    }

    private fun createFilterSpinnerOnItemSelectedListener() =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                v: View?,
                position: Int,
                id: Long
            ) {
                when (adapterView?.tag) {
                    FilterCategory.BRAND -> brandSelected(brandsFilterItems[position])
                    FilterCategory.PRICE -> {}
                    FilterCategory.SIZE -> {}
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

    private fun brandSelected(brand: String) {
        viewModel.setSelectedBrand(brand)
    }

    private fun createSpinnerAdapter(items: List<String>) =
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            items
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

    private fun setupRecyclerView() {
        with(binding.rvMain) {
            adapter = compositeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
        }
    }

}