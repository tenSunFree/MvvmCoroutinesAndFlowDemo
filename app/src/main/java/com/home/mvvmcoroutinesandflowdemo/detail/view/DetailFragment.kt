package com.home.mvvmcoroutinesandflowdemo.detail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.home.mvvmcoroutinesandflowdemo.R
import com.home.mvvmcoroutinesandflowdemo.common.base.*
import com.home.mvvmcoroutinesandflowdemo.common.custom.`typealias`.DetailEntityList
import com.home.mvvmcoroutinesandflowdemo.common.custom.`typealias`.DetailEntityListResults
import com.home.mvvmcoroutinesandflowdemo.common.custom.errors.ErrorHandler
import com.home.mvvmcoroutinesandflowdemo.common.custom.views.IndefiniteSnackbar
import com.home.mvvmcoroutinesandflowdemo.common.custom.views.SpacesItemDecoration
import com.home.mvvmcoroutinesandflowdemo.detail.viewmodel.DetailViewModel
import com.home.mvvmcoroutinesandflowdemo.menu.view.MenuActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class DetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val forecastsViewModel: DetailViewModel by viewModels { viewModelFactory }
    private lateinit var forecastsAdapter: DetailAdapter
    private lateinit var forecastsRecycler: RecyclerView
    private lateinit var pbForecasts: ContentLoadingProgressBar
    @ExperimentalCoroutinesApi
    private val observer = Observer<DetailEntityListResults> { handleResponse(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return view ?: inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity as MenuActivity).menuComponent?.inject(this)
        forecastsViewModel.forecastLiveData.observe(viewLifecycleOwner, observer)
        forecastsViewModel.cancelIfActive()
        getForecasts()
        super.onActivityCreated(savedInstanceState)
    }

    @ExperimentalCoroutinesApi
    private fun getForecasts() {
        IndefiniteSnackbar.hide()
        forecastsViewModel.getForecasts()
    }

    private fun initViews(view: View) {
        forecastsAdapter = DetailAdapter()
        view.apply {
            forecastsRecycler = findViewById(R.id.recycler_view)
            forecastsRecycler.apply {
                addItemDecoration(SpacesItemDecoration(resources.getDimension(R.dimen.margin).toInt()))
                adapter = forecastsAdapter
            }
            pbForecasts = findViewById(R.id.content_loading_progress_bar)
        }
    }

    @ExperimentalCoroutinesApi
    private fun handleResponse(it: Result<DetailEntityList>) {
        when (it) {
            is Success<DetailEntityList> -> bindData(it.data)
            is Error -> view?.let { view ->
                ErrorHandler.handleError(
                    view,
                    it,
                    shouldShowSnackBar = true,
                    refreshAction = { getForecasts() })
            }
            is Progress -> {
                pbForecasts.visibility = toggleVisibility(it)
            }
        }
    }

    private fun bindData(forecasts: DetailEntityList) {
        forecastsAdapter.submitList(forecasts)
    }
}