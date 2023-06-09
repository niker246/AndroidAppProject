package com.infinitumcode.mymovieapp.view.ui.find

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.paging.LivePagedListBuilder
import com.infinitumcode.mymovieapp.domain.pagination.MovieDataSourceFactory
import com.infinitumcode.mymovieapp.domain.pagination.PaginationState
import com.infinitumcode.mymovieapp.domain.repository.MovieRepository
import com.infinitumcode.mymovieapp.util.Constants
import com.infinitumcode.mymovieapp.view.base.BaseViewModel
import java.util.HashMap
import javax.inject.Inject

class FindViewModel @Inject constructor(private val repository: MovieRepository) : BaseViewModel() {
var queryString = Constants.QUERY_STRING
    private var movieDataSourceFactory: MovieDataSourceFactory =
        MovieDataSourceFactory(
            MovieRepository.QUERYTAG.SEARCH,
            initParams(),
            repository
        )

    private fun initParams(): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        hashMap[queryString] = ""
        return hashMap
    }

    val moviePagedLiveData = pagedConfig.let {
        LivePagedListBuilder(
            movieDataSourceFactory,
            it
        ).build()
    }

    val paginationState: LiveData<PaginationState>? =
        movieDataSourceFactory.movieDataSourceLiveData.switchMap { it.getPaginationState() }

    fun searchMovieByName(query: String) {
        val querySearch = HashMap<String, String>()
        if (query == movieDataSourceFactory.getLastQueryParams()[queryString]) return
        querySearch[queryString] = query
        movieDataSourceFactory.updateQueryParams(querySearch, MovieRepository.QUERYTAG.SEARCH)
    }

    /**
     * Retry possible last paged request (ie: network issue)
     */
    fun refreshFailedRequest() =
        movieDataSourceFactory.getSource()?.retryFailedQuery()

    /**
     * Refreshes all list after an issue
     */
    fun refreshAllList() =
        movieDataSourceFactory.getSource()?.refresh()


}