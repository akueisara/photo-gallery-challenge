package com.example.photogallerychallenge.ui.photos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.photogallerychallenge.data.network.UnsplashApi
import com.example.photogallerychallenge.databinding.FragmentPhotosBinding
import com.example.revoluttask.ViewModelFactory
import timber.log.Timber

class PhotosFragment : Fragment() {

    private val viewModel by viewModels<PhotosViewModel> { ViewModelFactory(UnsplashApi.unsplashApiService) }

    private lateinit var viewDataBinding: FragmentPhotosBinding

    private lateinit var listAdapter: PhotosAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentPhotosBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = PhotosAdapter(viewModel)
            viewDataBinding.photosList.adapter = listAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }
}