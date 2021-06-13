package dev.darshn.androidtesting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dev.darshn.androidtesting.R
import dev.darshn.androidtesting.adapters.ImageAdapter
import dev.darshn.androidtesting.databinding.FragmentImagePickBinding
import dev.darshn.androidtesting.util.Constants.GRID_COUNT
import javax.inject.Inject


class ImagePickFragment @Inject constructor(
     val imagAdapter : ImageAdapter
) : Fragment() {

    lateinit var binding : FragmentImagePickBinding
    lateinit var viewModel: ShoppingViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImagePickBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        initUI()
        return binding.root
    }


    private fun initUI(){
        imagAdapter.setOnClickItemListener {
            findNavController().popBackStack()
            viewModel.setCurImageUrl(it)
        }


        binding.rvImages.apply {
            adapter = imagAdapter
            layoutManager = GridLayoutManager(requireContext(),GRID_COUNT)
        }
    }

}