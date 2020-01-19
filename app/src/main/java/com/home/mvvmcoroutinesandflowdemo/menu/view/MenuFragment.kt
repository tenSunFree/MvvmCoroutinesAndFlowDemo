package com.home.mvvmcoroutinesandflowdemo.menu.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.home.mvvmcoroutinesandflowdemo.R
import com.home.mvvmcoroutinesandflowdemo.common.base.BaseFragment
import com.home.mvvmcoroutinesandflowdemo.common.custom.views.IndefiniteSnackbar
import com.home.mvvmcoroutinesandflowdemo.common.custom.views.SpacesItemDecoration
import com.home.mvvmcoroutinesandflowdemo.menu.viewmodel.MenuViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.jetbrains.anko.toast
import javax.inject.Inject

class MenuFragment : BaseFragment() {

    companion object {
        const val MARS_ROVER_PHOTOS = 0
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val menuViewModel: MenuViewModel by viewModels { viewModelFactory }
    private lateinit var imageView: ImageView
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var recyclerView: RecyclerView
    @ExperimentalCoroutinesApi
    private val observer = Observer<List<String>> { handleResponse(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return view ?: inflater.inflate(
            R.layout.fragment_menu, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity as MenuActivity).menuComponent?.inject(this)
        menuViewModel.openMutableLiveData.observe(viewLifecycleOwner, observer)
        getWeather()
        super.onActivityCreated(savedInstanceState)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun getWeather() {
        IndefiniteSnackbar.hide()
        menuViewModel.getWeather()
    }

    @ExperimentalCoroutinesApi
    private fun handleResponse(result: List<String>) {
        bindData(result)
    }

    private fun initViews(view: View) {
        menuAdapter = MenuAdapter()
        menuAdapter.setOnItemClickListener = { position: Int, name: String ->
            if (position == MARS_ROVER_PHOTOS) {
                findNavController().navigate(R.id.action_weatherFragment_to_forecastsFragment)
            } else {
                context?.toast("Click $name")
            }
        }
        view.apply {
            recyclerView = findViewById(R.id.recycler_view)
            recyclerView.apply {
                addItemDecoration(SpacesItemDecoration(resources.getDimension(R.dimen.margin).toInt()))
                adapter = menuAdapter
            }
            imageView = findViewById(R.id.image_view)
            imageView.apply {
                Glide.with(this).load(R.drawable.icon_fragment_menu_background).into(this)
            }
        }
    }

    private fun bindData(response: List<String>) {
        menuAdapter.submitList(response)
    }
}
