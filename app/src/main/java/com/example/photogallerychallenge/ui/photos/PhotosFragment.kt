package com.example.photogallerychallenge.ui.photos

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.photogallerychallenge.PhotoGalleryApplication
import com.example.photogallerychallenge.R
import com.example.photogallerychallenge.util.EventObserver
import com.example.photogallerychallenge.data.local.prefs.PreferencesHelper
import com.example.photogallerychallenge.databinding.FragmentPhotosBinding
import com.example.photogallerychallenge.ViewModelFactory
import com.example.photogallerychallenge.util.hideKeyboard
import com.example.photogallerychallenge.util.setInfoText
import kotlinx.android.synthetic.main.fragment_photos.*
import timber.log.Timber
import java.net.HttpURLConnection

class PhotosFragment : Fragment() {

    private val viewModel by viewModels<PhotosViewModel> { ViewModelFactory((requireContext().applicationContext as PhotoGalleryApplication).unsplashRepository) }

    private lateinit var viewDataBinding: FragmentPhotosBinding

    private lateinit var listAdapter: PhotosAdapter

    private lateinit var layoutManager: StaggeredGridLayoutManager

    private var photoViewType: Int = PhotoViewType.LIST.value

    private lateinit var lastQueryString: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentPhotosBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_view_type -> {
                onClickViewTypeMenuItemAction(item)
                true
            }
            else -> false
        }

    private fun onClickViewTypeMenuItemAction(item: MenuItem) {
        context?.let {
            photoViewType = PhotoViewType.switchViewTypeValue(photoViewType)
            layoutManager.spanCount = photoViewType
            item.icon = ContextCompat.getDrawable(it, PhotoViewType.setMenuImageResId(photoViewType))
            PreferencesHelper(it).setPhotoViewType(photoViewType)
            listAdapter.notifyItemRangeChanged(0, listAdapter.itemCount)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_photos_fragment, menu)
        // initialize view type menu
        context?.let {
            photoViewType = PreferencesHelper(it).getPhotoViewType()
            val viewTypeMenu = menu.findItem(R.id.menu_view_type)
            viewTypeMenu.icon = ContextCompat.getDrawable(it, PhotoViewType.setMenuImageResId(photoViewType))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        initListAdapter()
        setupNavigation()

        context?.let {
            lastQueryString = PreferencesHelper(it).getLastQuery()
            viewModel.loadPhotos(lastQueryString)
            initSearch(lastQueryString)
        }
    }

    private fun initSearch(query: String) {
        search_repo.setText(query)

        search_repo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                hideKeyboard()
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        search_repo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {
        search_repo.text.trim().let { charSequence ->
            if(lastQueryString != charSequence.toString()) {
                Timber.d("Query: $charSequence")
                lastQueryString = charSequence.toString()
                context?.let { PreferencesHelper(it).setLastQuery(charSequence.toString()) }
                photos_recycler_view.smoothScrollToPosition(0)
                viewModel.loadPhotos(charSequence.toString(), true)
                listAdapter.submitList(null)
            }
        }
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
                viewDataBinding.photosRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if(recyclerViewReachTheEnd(viewDataBinding.photosRecyclerView)) {
                            viewModel.reloadPhotos()
                        }
                    }
                })
                viewModel.error.observe(this, Observer {
                    if(it?.code != null && it.code == HttpURLConnection.HTTP_FORBIDDEN) {
                        Toast.makeText(context, getString(R.string.api_rate_limit_error), Toast.LENGTH_SHORT).show()
                    }
                })
            }
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun recyclerViewReachTheEnd(recyclerView: RecyclerView) : Boolean {
        return !recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)
    }
}