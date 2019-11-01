package com.example.photogallerychallenge.ui.photodetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.photogallerychallenge.Injection
import com.example.photogallerychallenge.R

import com.example.photogallerychallenge.databinding.FragmentPhotoDetailBinding
import com.example.photogallerychallenge.util.EventObserver
import com.example.revoluttask.ViewModelFactory
import java.net.HttpURLConnection

class PhotoDetailFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentPhotoDetailBinding

    private val viewModel by viewModels<PhotoDetailViewModel> { ViewModelFactory(Injection.provideUnsplashRepository(context!!)) }

    private val args: PhotoDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentPhotoDetailBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            photoId = args.photoId
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        viewModel.getPhoto(args.photoId)

        viewModel.error.observe(this, Observer {
            if(it != null && it.message.contains(HttpURLConnection.HTTP_FORBIDDEN.toString())) {
                Toast.makeText(
                    context,
                    getString(R.string.api_rate_limit_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.photoViewerImageViewUrl.observe(this, Observer {
            openPhotoViewer(it)
        })

        return viewDataBinding.root
    }

    private fun openPhotoViewer(imageUrl: String) {
        context?.let {
            PhotoViewerDialog(it, imageUrl).show()
        }
    }
}
