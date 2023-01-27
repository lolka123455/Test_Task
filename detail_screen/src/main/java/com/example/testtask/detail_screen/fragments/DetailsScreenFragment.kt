package com.example.testtask.detail_screen.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.testtask.detail_screen.R
import com.example.testtask.state_network_connection.R as R2
import com.example.testtask.detail_screen.databinding.FragmentDetailsBinding
import com.example.testtask.detail_screen.entities.ProductDetails
import com.example.testtask.detail_screen.viewmodel.DetailsViewModel
import com.example.testtask.detail_screen.adapters.ProductImagesViewPagerAdapter
import com.example.testtask.navigation.AppScreens
import com.example.testtask.state_network_connection.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class DetailsScreenFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val viewModel by viewModel<DetailsViewModel>()

    private val adapter = ProductImagesViewPagerAdapter()

    private var colorImageViews: List<ImageView> = emptyList()
    private lateinit var chosenColorImageView: ImageView


    private var capacityTextViews: List<TextView> = emptyList()
    private lateinit var chosenCapacityTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupColorAndCapacityViews()
        setupViewPager()
        observe()
    }

    /**
     * This function sets up the ViewPager for the DetailsScreenFragment.
     * It sets the adapter for the ViewPager to the adapter of the DetailsScreenFragment,
     * sets up the transformer for the ViewPager,
     * sets the offscreen page limit to 3,disables clipping for the ViewPager and its children,
     * and sets the overscroll mode to "never" for the first child of the ViewPager.
     */

    private fun setupViewPager() {
        val viewPager = binding.viewPager
        viewPager.adapter = this@DetailsScreenFragment.adapter
        viewPager.setupTransformer()
        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    /**
     * Applies a page transformer to the ViewPager2 that scales the Y-axis of the pages
     * based on their position.
     *
     * The scale of pages on the side (not the front page) will be set to `sidePageScaleY` (0.8 by default).
     * The scale of the front page will be set to 1.
     * The scale of pages in between will be interpolated based on their position.
     *
     * @param sidePageScaleY The scale factor to apply to pages on the side. Default is 0.8.
     */

    private fun ViewPager2.setupTransformer() {
        val sidePageScaleY = 0.8f
        val frontPageScaleY = 1 - sidePageScaleY
        setPageTransformer { page, position ->
            val isFrontPage = 1 - abs(position)
            page.scaleY = sidePageScaleY + isFrontPage * frontPageScaleY
        }
    }

    private fun setupColorAndCapacityViews() {
        chosenColorImageView = binding.firstColorImageView
        chosenCapacityTextView = binding.firstCapacity
        colorImageViews = with(binding) {
            listOf(firstColorImageView, secondColorImageView)
        }
        capacityTextViews = with(binding) {
            listOf(firstCapacity, secondCapacity)
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        setColorsClickListener()
        setCapacityClickListener()
        setBackViewClickListener()
        setCartClickListener()
    }

    private fun setCartClickListener() {
        binding.cartImageView.setOnClickListener {
            findNavController().navigate(AppScreens.CartScreen)
        }
    }

    private fun setBackViewClickListener() {
        binding.backImageView.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setCapacityClickListener() {
        capacityTextViews.forEach { it ->
            it.setOnClickListener {
                resetCapacitySelection()
                selectCapacity(it as TextView)
            }
        }
    }

    private fun setColorsClickListener() {
        colorImageViews.forEach { it ->
            it.setOnClickListener {
                unselectChosenColor()
                selectColor(it as ImageView)
            }
        }
    }

    private fun selectCapacity(capacityToSelect: TextView) {
        setCapacityBackground(capacityToSelect, R.color.orange)
        setCapacityTextColor(capacityToSelect)
        chosenCapacityTextView = capacityToSelect
    }

    private fun setCapacityBackground(capacity: TextView, color: Int) {
        capacity.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), color)
        )
    }

    private fun setCapacityTextColor(capacity: TextView) {
        capacity.setTextColor(Color.WHITE)
    }

    //I had to split one function into three
    // This will improve readability and the ability to expand if needed

    private fun resetCapacitySelection() {
        clearCapacityBackground()
        resetCapacityTextColor()
    }

    private fun clearCapacityBackground() {
        chosenCapacityTextView.backgroundTintList = null
    }

    private fun resetCapacityTextColor() {
        chosenCapacityTextView.setTextColor(Color.DKGRAY)
    }

    private fun selectColor(colorToSelect: ImageView) {
        colorToSelect.setImageResource(R.drawable.ic_selected)
        chosenColorImageView = colorToSelect
    }

    private fun unselectChosenColor() {
        chosenColorImageView.setImageDrawable(null)
    }

    private fun observe() {
        with(viewModel) {
            productDetails.onEach {
                displayProductDetails(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            uiState.onEach {
                updateUiState(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    /**
     * This function is used to fill the UI elements with the details of a product.
     * @param details: ProductDetails? - an object containing all the details of the product.
     */

    private fun displayProductDetails(details: ProductDetails?) {
        with(binding) {

            if (details == null) return

            titleTextView.text = details.title
            ratingBar.rating = details.rating.toFloat()
            cpuTextView.text = details.cpu
            cameraTextView.text = details.camera
            ramTextView.text = details.ram
            sdTextView.text = details.sd
            priceTextView.text = details.price.toPriceFormat()

            favouriteImageView.setImageResource(
                if (details.isFavorites) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )

            setDetailsInformation(details)
        }
    }

    private fun setDetailsInformation(details: ProductDetails) {
        setProductColors(details)
        setProductCapacity(details)
        adapter.currentList = details.images
    }

    /**
     * This function updates the capacity of the product in the UI.
     * @param details: ProductDetails - A data class that contains the product information.
     * @property capacity: List<Int> - A list of integers representing the capacity of the product.
     *
     * @see ProductDetails
     *
     */

    private fun setProductCapacity(details: ProductDetails) {
        details.capacity.forEachIndexed { i, capacity ->
            with(capacityTextViews[i]) {
                makeVisible()
                text = "$capacity GB"
            }
        }
    }

    /**
     *Set the colors of the product based on the details provided.
     * @param details The details of the product that contain the colors information
     * This function uses a traditional for loop to iterate through the colors of the product.
     * It sets the background tint of the image views to the corresponding color.
     * It also makes the image views visible to display the colors.
     */

    private fun setProductColors(details: ProductDetails) {
        val colorList = details.color
        val colorImageViews = colorImageViews

        val size = colorList.size
        for (i in 0 until size) {
            colorImageViews[i].apply {
                makeVisible()
                backgroundTintList = ColorStateList.valueOf(Color.parseColor(colorList[i]))
            }
        }
    }

    private fun updateUiState(state: UiState) {
        when (state) {
            is UiState.Success -> with(binding) {
                successStateUi.visibility = View.VISIBLE
                errorStateUi.errorStateUi.visibility = View.GONE
            }
            is UiState.Error -> with(binding) {
                successStateUi.visibility = View.GONE
                errorStateUi.errorStateUi.visibility = View.VISIBLE
                setTryAgainButtonClickListener()
            }
            is UiState.Loading -> with(binding) {
                successStateUi.visibility = View.VISIBLE
            }
        }
    }

    private fun setTryAgainButtonClickListener() {

        // Because of a conflict during import I had to do this -
        // import com.example.testtask.state_network_connection.R as R2

        val tryAgainTextView = view?.findViewById<FrameLayout>(R2.id.tryAgainFrame)
        tryAgainTextView?.setOnClickListener {
            viewModel.retryNetworkCall()
        }
    }

    private fun View.makeVisible() {
        visibility = View.VISIBLE
    }

    /**
     *Converts an Int to a formatted price String.
     *@return Formatted price String.
     *If the input Int is greater than or equal to 1000, the format is "$x,xxx.00".
     *If the input Int is less than 1000, the format is "x.00".
     */

    private fun Int.toPriceFormat(): String =
        if (this >= 1000) {
            "${"$%.${3}f".format(this / 1000.0)}.00"
        } else "$$this.00"
}