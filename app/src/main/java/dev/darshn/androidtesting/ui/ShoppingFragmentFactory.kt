package dev.darshn.androidtesting.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import dev.d.AddShoppingItemFragment
import dev.darshn.androidtesting.adapters.ImageAdapter
import dev.darshn.androidtesting.adapters.ShoppingItemAdapter
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide :RequestManager,
    private val adapter: ShoppingItemAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(adapter,null)

            else->super.instantiate(classLoader, className)
        }
    }

}