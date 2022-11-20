package com.example.testtask.cart_screen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.cart_screen.adapters.CartItemsAdapter
import com.example.testtask.cart_screen.databinding.FragmentCartBinding
import com.example.testtask.cart_screen.viewmodels.CartScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartScreenFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel by viewModel<CartScreenViewModel>()
    private val adapter = CartItemsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBackBtn.setOnClickListener { requireActivity().onBackPressed() }

        observe()
        setupRecyclerView()
    }

    private fun observe() {
        with(viewModel) {
            cartItems.observe(viewLifecycleOwner) {
                adapter.currentList = it
            }
            total.observe(viewLifecycleOwner) {
                binding.totalTextView.text = it.toPriceFormat()
            }
            delivery.observe(viewLifecycleOwner) {
                binding.deliveryTextView.text = it
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvCartItems) {
            adapter = this@CartScreenFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun Int.toPriceFormat(): String =
        if (this >= 1000) {
            "$%.${3}f us".format(this / 1000.0)
        } else "$$this us"

}