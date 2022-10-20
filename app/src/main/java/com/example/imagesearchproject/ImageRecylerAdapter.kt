package com.example.imagesearchproject

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchproject.databinding.ItemImagesBinding
import com.example.imagesearchproject.room.ImageDatabase
import com.example.imagesearchproject.room.ImageItem
import kotlinx.coroutines.*
import java.io.BufferedInputStream
import java.lang.Runnable
import java.net.MalformedURLException
import java.net.URL

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

    fun updateList(list: List<ImageItem>) {
        items.clear()
        items.addAll(list)
        this.notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemImagesBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageItem) {
            with(binding) {
                imageitem = item
                val imageScope = CoroutineScope(Dispatchers.IO)
                imageScope.launch {
                    try {
                        imgView.setImageBitmap(convertedURLtoBitmap(item.url))
                    } catch (e: MalformedURLException) {

                    } catch (e: Exception) {
                        Log.d("ImageError02", "Error msg : $e")
                    }
                    imageScope.cancel()
                }
                layoutFollow.setOnClickListener {
                    val runable = Runnable {
                        try {
                            item.follow = item.follow?.plus(1)
                            val db = ImageDatabase.getInstance(App.context())
                            val dao = db?.imageDao()
                            dao?.update(item)
                            imageitem = item
                            executePendingBindings()
                        } catch (e: Exception) {
                            Log.d("error", "Error - $e")
                        }
                    }

                    val thread = Thread(runable)
                    thread.start()
                }
                executePendingBindings()
            }
        }

        private fun convertedURLtoBitmap(imageURL: String?): Bitmap {
            val url = URL(imageURL)
            val conn = url.openConnection()
            conn.connect()
            val size = conn.contentLength
            val bis = BufferedInputStream(conn.getInputStream(), size)
            val imgBitmap = BitmapFactory.decodeStream(bis)
            bis.close()
            return imgBitmap
        }
    }
}