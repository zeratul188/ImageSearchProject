package com.example.imagesearchproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchproject.databinding.ItemImagesBinding
import com.example.imagesearchproject.room.ImageItem

class ImageRecyclerAdapter(
    private val items: ArrayList<ImageItem>
): RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImagesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            /*val layoutParams = itemView.layoutParams
            layoutParams.height = 30
            layoutParams.width = 30
            itemView.requestLayout()*/
            bind(item)
            itemView.tag = item
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: ItemImagesBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageItem) {
            with(binding) {
                imageitem = item
                executePendingBindings()
            }
        }
    }
}