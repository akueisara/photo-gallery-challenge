package com.example.photogallerychallenge.ui.photos

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import com.example.photogallerychallenge.Injection
import com.example.photogallerychallenge.util.EventObserver
import com.example.photogallerychallenge.R
import com.example.photogallerychallenge.data.database.DatabasePhoto
import com.example.photogallerychallenge.databinding.FragmentPhotosBinding
import com.example.revoluttask.ViewModelFactory
import timber.log.Timber

class PhotosFragment : Fragment() {

    private val viewModel by viewModels<PhotosViewModel> { ViewModelFactory(Injection.provideUnsplashRepository(context!!)) }

    private lateinit var viewDataBinding: FragmentPhotosBinding

    private lateinit var listAdapter: PhotosAdapter

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
            else -> false
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_photos_fragment, menu)
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
            listAdapter = PhotosAdapter(viewModel)
            viewDataBinding.photosList.adapter = listAdapter
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
}