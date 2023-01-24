package com.example.testtask.cart_screen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.cart_screen.databinding.FragmentCartBinding
import com.example.testtask.cart_screen.adapters.CartItemsAdapter
import com.example.testtask.cart_screen.viewmodel.CartViewModel
import com.example.testtask.state_network_connection.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartScreenFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModel()
    private val adapter: CartItemsAdapter by lazy { CartItemsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backImageView.setOnClickListener { requireActivity().onBackPressed() }

        observe()
        setupRecyclerView()
    }

    private fun observe() {
        with(viewModel) {
            uiState.onEach {
                updateUiState(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            cartItems.onEach {
                adapter.currentList = it
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            total.onEach {
                binding.totalTextView.text = it.toPriceFormat()
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            delivery.onEach {
                binding.deliveryTextView.text = it
            }.launchIn(viewLifecycleOwner.lifecycleScope)
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
        view?.let {
            it.findViewById<FrameLayout>(
                com.example.testtask.state_network_connection.R.id.tryAgainFrame
            )?.let { tryAgainTextView ->
                tryAgainTextView.setOnClickListener {
                    viewModel.retryNetworkCall()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            adapter = this@CartScreenFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun Int.toPriceFormat(): String =
        if (this >= 1000) "${this / 1000.0}K us" else "$this us"
}