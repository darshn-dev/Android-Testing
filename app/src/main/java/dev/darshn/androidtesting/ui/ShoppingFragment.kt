package dev.darshn.androidtesting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.RIGHT
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.darshn.androidtesting.R
import dev.darshn.androidtesting.adapters.ShoppingItemAdapter
import dev.darshn.androidtesting.databinding.FragmentShoppingBinding
import kotlinx.android.synthetic.main.item_shopping.*
import javax.inject.Inject


@AndroidEntryPoint
class ShoppingFragment  @Inject constructor(
    val shoppingItemAdapter: ShoppingItemAdapter,
    var viewModel:ShoppingViewModel?
): Fragment() {

    private lateinit var binding : FragmentShoppingBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShoppingBinding.inflate(inflater)
        initUI()
        subscriber()
        return binding.root
    }

    private fun initUI(){
        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        binding.rvShoppingItems.apply {
            adapter = shoppingItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
        binding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(R.id.action_shoppingFragment_to_addShoppingItemFragment)
        }
    }


    private fun subscriber(){
        viewModel?.shoppingItems?.observe(viewLifecycleOwner, Observer {
            shoppingItemAdapter.shoppingItems = it
        })

        viewModel?.totalPrice?.observe(viewLifecycleOwner, Observer {
            val price = it?: 0f
            val priceText = "Total Price $price"
            tvShoppingItemPrice.text = priceText

        })
    }


    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, LEFT or RIGHT
    ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = shoppingItemAdapter.shoppingItems[pos]
            viewModel?.deleteShoppingItem(item)
            Snackbar.make(requireView(),"deleted", Snackbar.LENGTH_SHORT).apply {
                setAction("Undo"){
                    viewModel?.insertItem(item)

                }
                show()
            }
        }
    }




}