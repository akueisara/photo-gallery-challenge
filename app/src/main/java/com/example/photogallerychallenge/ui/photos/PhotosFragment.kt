package com.example.photogallerychallenge.ui.photos

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.photogallerychallenge.Injection
import com.example.photogallerychallenge.R
import com.example.photogallerychallenge.util.EventObserver
import com.example.photogallerychallenge.data.local.database.DatabasePhoto
import com.example.photogallerychallenge.data.local.prefs.PreferencesHelper
import com.example.photogallerychallenge.databinding.FragmentPhotosBinding
import com.example.revoluttask.ViewModelFactory
import timber.log.Timber


class PhotosFragment : Fragment() {

    private val viewModel by viewModels<PhotosViewModel> { ViewModelFactory(Injection.provideUnsplashRepository(context!!)) }

    private lateinit var viewDataBinding: FragmentPhotosBinding

    private lateinit var listAdapter: PhotosAdapter

    private lateinit var layoutManager: StaggeredGridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentPhotosBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_refresh -> {
                viewModel.refreshLoadPhotos()
                true
            }
            R.id.menu_view_type -> {
                context?.let {
                    val photoViewType = PreferencesHelper(it).getPhotoViewType()
                    layoutManager.spanCount = PhotoViewType.switchViewTypeValue(photoViewType)
                    item.icon = ContextCompat.getDrawable(it, PhotoViewType.setMenuImageResId(photoViewType))
                    PreferencesHelper(it).setPhotoViewType(PhotoViewType.switchViewTypeValue(photoViewType))
                    listAdapter.notifyItemRangeChanged(0, listAdapter.itemCount)
                }
                true
            }
            else -> false
        }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_photos_fragment, menu)
        context?.let {
            val photoViewType = PreferencesHelper(it).getPhotoViewType()
            val viewTypeMenu = menu.findItem(R.id.menu_view_type)
            viewTypeMenu.icon = ContextCompat.getDrawable(it, PhotoViewType.setMenuImageResId(photoViewType, true))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        initListAdapter()
        setupNavigation()
    }

    private fun setupNavigation() {
        viewModel.openPhotoEvent.observe(this, EventObserver {
                openPhotoDetails(it)
        })
    }

    private fun openPhotoDetails(photoId: String) {
        val action = PhotosFragmentDirections.actionPhotosToPhotoDetail(photoId)
        findNavController().navigate(action)
    }

    private fun initListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            context?.let {
                layoutManager = StaggeredGridLayoutManager(PreferencesHelper(it).getPhotoViewType(), LinearLayoutManager.VERTICAL)
                viewDataBinding.photosRecyclerView.layoutManager = layoutManager
                listAdapter = PhotosAdapter(viewModel, it)
                viewDataBinding.photosRecyclerView.adapter = listAdapter
            }

            viewModel.photos.observe(this, Observer<PagedList<DatabasePhoto>> {
                Timber.d("list: ${it?.size}")
                listAdapter.submitList(it)
            })

            viewModel.networkErrors.observe(this, Observer {
                viewDataBinding.errorTextView.text = it.message
            })
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}