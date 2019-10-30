package com.example.photogallerychallenge.ui.photodetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.photogallerychallenge.Injection

import com.example.photogallerychallenge.R
import com.example.photogallerychallenge.databinding.FragmentPhotoDetailBinding
import com.example.photogallerychallenge.ui.photos.PhotosViewModel
import com.example.revoluttask.ViewModelFactory

class PhotoDetailFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentPhotoDetailBinding

    private val args: PhotoDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<PhotoDetailViewModel> { ViewModelFactory(Injection.provideUnsplashRepository(context!!)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentPhotoDetailBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.start(args.photoId)

        viewModel.photo.observe(this, Observer {
            if(it != null) {
                viewModel.getUser(it.user_id)
            }
        })

        return viewDataBinding.root
    }

}
