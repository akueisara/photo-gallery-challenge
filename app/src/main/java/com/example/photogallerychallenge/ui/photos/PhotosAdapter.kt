package com.example.photogallerychallenge.ui.photos

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photogallerychallenge.R
import com.example.photogallerychallenge.data.local.database.DatabasePhoto
import com.example.photogallerychallenge.data.local.prefs.PreferencesHelper
import com.example.photogallerychallenge.databinding.GridPhotoItemBinding
import com.example.photogallerychallenge.databinding.ListPhotoItemBinding

enum class PhotoViewType(val value: Int) {
    LIST(1),
    GRID(3);

    companion object{
        fun switchViewTypeValue(value: Int): Int {
            return if(value == LIST.value) {
                GRID.value
            } else {
                LIST.value
            }
        }

        fun setMenuImageResId(value: Int, theOppositeResult: Boolean = false): Int {
            return if(!theOppositeResult.xor(value != LIST.value)) R.drawable.ic_list else R.drawable.ic_grid
        }
    }
}

class PhotosAdapter(private val viewModel: PhotosViewModel, private val context: Context) : PagedListAdapter<DatabasePhoto, RecyclerView.ViewHolder>(PhotoDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return PreferencesHelper(context).getPhotoViewType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        if (PreferencesHelper(context).getPhotoViewType() == PhotoViewType.LIST.value) {
            (holder as ListViewHolder).bind(viewModel, item)
        } else {
            (holder as GridViewHolder).bind(viewModel, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PhotoViewType.LIST.value -> ListViewHolder.from(parent)
            else -> GridViewHolder.from(parent)
        }
    }

    class ListViewHolder private constructor(private val binding: ListPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: PhotosViewModel, item: DatabasePhoto) {
            binding.viewmodel = viewModel
            binding.photo = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListPhotoItemBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(binding)
            }
        }
    }

    class GridViewHolder private constructor(private val binding: GridPhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: PhotosViewModel, item: DatabasePhoto) {
            binding.viewmodel = viewModel
            binding.photo = item
            binding.executePendingBindings()

            loadAndAdjustImage(binding.photoImageView, item)
        }

        private fun loadAndAdjustImage(imageView: ImageView, photo: DatabasePhoto) {
            val layoutParams = imageView.layoutParams
            layoutParams.height = (photo.height.toFloat() / photo.width.toFloat() * getScreenWidth(imageView.context) / PhotoViewType.GRID.value).toInt()
            layoutParams.width =  getScreenWidth(imageView.context) / PhotoViewType.GRID.value
            imageView.layoutParams = layoutParams

            Glide.with(binding.photoImageView.context)
                .load(photo.urls.small)
                .override(layoutParams.width, layoutParams.height)
                .into(binding.photoImageView)
        }

        companion object {

            fun from(parent: ViewGroup): GridViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridPhotoItemBinding.inflate(layoutInflater, parent, false)
                return GridViewHolder(binding)
            }

            fun getScreenWidth(context: Context): Int {
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val outMetrics = DisplayMetrics()
                wm.defaultDisplay.getMetrics(outMetrics);
                return outMetrics.widthPixels
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