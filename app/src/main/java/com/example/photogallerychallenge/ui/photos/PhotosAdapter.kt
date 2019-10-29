package com.example.photogallerychallenge.ui.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallerychallenge.data.database.DatabasePhoto
import com.example.photogallerychallenge.data.model.Photo
import com.example.photogallerychallenge.databinding.PhotoItemBinding

class PhotosAdapter(private val viewModel: PhotosViewModel) : PagedListAdapter<DatabasePhoto, PhotosAdapter.ViewHolder>(PhotoDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: PhotosViewModel, item: DatabasePhoto) {
            binding.viewmodel = viewModel
            binding.photo = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PhotoItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PhotoDiffCallback : DiffUtil.ItemCallback<DatabasePhoto>() {
    override fun areItemsTheSame(oldItem: DatabasePhoto, newItem: DatabasePhoto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DatabasePhoto, newItem: DatabasePhoto): Boolean {
        return oldItem == newItem
    }
}