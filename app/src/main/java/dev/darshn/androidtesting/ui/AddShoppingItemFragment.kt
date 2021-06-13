package dev.d

import dev.darshn.androidtesting.ui.ShoppingViewModel


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import dev.darshn.androidtesting.R
import dev.darshn.androidtesting.databinding.FragmentAddShoppingItemBinding
import dev.darshn.androidtesting.util.Status
import kotlinx.android.synthetic.main.fragment_shopping.*
import kotlinx.android.synthetic.main.fragment_shopping.view.*
import kotlinx.android.synthetic.main.item_image.*
import javax.inject.Inject



class AddShoppingItemFragment @Inject constructor(
    val glide:RequestManager
)  : Fragment() {



    lateinit var binding : FragmentAddShoppingItemBinding
    lateinit var viewModel: ShoppingViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddShoppingItemBinding.inflate(inflater)
       initUI()
        return  binding.root
    }

    private fun initUI(){
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        binding.ivShoppingImage.setOnClickListener {
            findNavController().navigate(R.id.action_addShoppingItemFragment_to_imagePickFragment)
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        subscriberToObservers()


        binding.btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItme(
                binding.etShoppingItemName.text.toString(),
                binding.etShoppingItemAmount.text.toString(),
                binding.etShoppingItemPrice.text.toString()
            )
        }

    }

    private fun subscriberToObservers(){
        viewModel.curImage.observe(viewLifecycleOwner, Observer {
            glide.load(it).into(ivShoppingImage)
        })

        viewModel.inserShoppingItem.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                when(it.status){
                    Status.SUCCESS ->{
                       Snackbar.make(requireView().rootLayout,"Added", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR->{
                        Snackbar.make(requireView().rootLayout,it.message?: "Error", Snackbar.LENGTH_SHORT).show()
                    }
                    Status.LOADING ->{
                        /* NO-OP */
                    }
                }
            }
        })
    }

}