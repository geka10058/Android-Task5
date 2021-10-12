package com.androidTask5.imagesearchapp.ui.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.androidTask5.imagesearchapp.R
import com.androidTask5.imagesearchapp.data.CatPhoto
import com.androidTask5.imagesearchapp.databinding.ItemCatPhotoBinding

class CatPhotoAdapter (private val listener:OnItemClickListener) :
    PagingDataAdapter<CatPhoto, CatPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemCatPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PhotoViewHolder(private val binding: ItemCatPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener{
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if(item !=null) {
                        listener.onItemClicked(item)
                    }
                }

            }
        }

        fun bind(photo: CatPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                textViewName.text = photo.id
                Log.d("AppDebug", "url: ${photo.url}")
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(photo: CatPhoto)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<CatPhoto>() {
            override fun areItemsTheSame(oldItem: CatPhoto, newItem: CatPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CatPhoto, newItem: CatPhoto) =
                oldItem == newItem
        }
    }
}