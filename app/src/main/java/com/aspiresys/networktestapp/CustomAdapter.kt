package com.aspiresys.testapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aspiresys.networktestapp.R
import com.bumptech.glide.Glide

class CustomAdapter(private var mList: List<ItemsItem>, private val context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private val baseUrl = "https://mediav2.oriana.com/catalog/product/cache/90bd6aa24a41856ed34bbcd6adf93745"

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = itemsViewModel.name
        val imageUrl = baseUrl + itemsViewModel.mediaGalleryEntries?.get(0)?.file

        Glide.with(context)
            .load(imageUrl)
            .into(holder.imageView)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}