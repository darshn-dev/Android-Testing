package dev.darshn.androidtesting.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import dev.darshn.androidtesting.R
import kotlinx.android.synthetic.main.item_image.view.*
import javax.inject.Inject

class ImageAdapter @Inject constructor(
    private val glide: RequestManager
) :RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {


    private val  diffCallBack = object :DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
          return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
          return oldItem == newItem
        }

    }


    private val differ = AsyncListDiffer(this,diffCallBack)

    var images: List<String>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    class ImageViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
      return ImageViewHolder(
          LayoutInflater.from(parent.context).inflate(
              R.layout.item_image,
              parent,
              false
          )
      )
    }


    private var onItemClickListener : ((String)->Unit) ? = null


    fun setOnClickItemListener(listener:(String)->Unit){

        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = images[position]
        holder.itemView.apply {
            glide.load(url).into(ivShoppingImage)

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(url)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return images.size
    }
}