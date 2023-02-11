package com.example.testtask.cart_screen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.cart_screen.adapters.CartItemAdapter
import com.example.testtask.cart_screen.databinding.FragmentCartBinding
import com.example.testtask.cart_screen.viewmodel.CartViewModel
import com.example.testtask.state_network_connection.R
import com.example.testtask.state_network_connection.UiState
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartScreenFragment : Fragment() {

    private val binding: FragmentCartBinding
        get() = _binding!!
    private var _binding: FragmentCartBinding? = null
    private val viewModel: CartViewModel by viewModel()

    private val itemAdapter: ItemAdapter<CartItemAdapter> = ItemAdapter()
    private val recyclerViewAdapter = FastAdapter.with(itemAdapter)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = recyclerViewAdapter
        binding.backImageView.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        observe()
        setupRecyclerView()
    }

    private fun observe() {
        observeUIState()
        observeCartItems()
        observeTotal()
        observeDelivery()
    }

    private fun observeUIState() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { updateUiState(it) }
        }
    }

    private fun observeCartItems() {
        lifecycleScope.launchWhenStarted {
            viewModel.cartItems.collect {
                FastAdapterDiffUtil[itemAdapter] = it.map { cartItem ->
                    CartItemAdapter(cartItem)
                }
            }
        }
    }

    private fun observeTotal() {
        lifecycleScope.launchWhenStarted {
            viewModel.total.collect { binding.totalTextView.text = it.toPriceFormat() }
        }
    }

    private fun observeDelivery() {
        lifecycleScope.launchWhenStarted {
            viewModel.delivery.collect { binding.deliveryTextView.text = it }
        }
    }

    private fun updateUiState(state: UiState) {
        when (state) {
            is UiState.Success -> with(binding) {
                successStateUi.visibility = View.VISIBLE
                errorStateUi.errorStateUi.visibility = View.GONE
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
            is UiState.Error -> with(binding) {
                successStateUi.visibility = View.GONE
                errorStateUi.errorStateUi.visibility = View.VISIBLE
                setTryAgainButtonClickListener()
            }
            is UiState.Loading -> with(binding) {
                successStateUi.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        }
    }

    private fun setTryAgainButtonClickListener() {
        val tryAgainTextView = view?.findViewById<FrameLayout>(R.id.tryAgainFrame)
        tryAgainTextView?.setOnClickListener {
            viewModel.retryNetworkCall()
        }
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            adapter = this@CartScreenFragment.recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    /**
     * Converts an integer to a price format string.
     *
     * @return a formatted string with the price, with "us" added at the end.
     * If the integer is greater than or equal to 1000, the value is divided by 1000 and
     * a "k" is appended to the end of the value before "us".
     */

    private fun Int.toPriceFormat(): String =
        if (this >= 1000) {
            "$%.${3}f us".format(this / 1000.0)
        } else "$$this us"

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}