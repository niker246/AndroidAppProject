package com.infinitumcode.mymovieapp.view.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.infinitumcode.mymovieapp.data.Resource
import com.infinitumcode.mymovieapp.data.Status
import com.infinitumcode.mymovieapp.domain.pojo.MovieResult
import com.infinitumcode.mymovieapp.domain.repository.MovieRepository
import com.infinitumcode.mymovieapp.view.base.BaseViewModel
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val repository: MovieRepository) :
    BaseViewModel() {


    fun getMovieFavorites(): LiveData<Resource<List<MovieResult>>> {
        progressState.postValue(true)
        return repository.allFavoriteMovie().map {
            progressState.postValue(false)
            Resource(Status.SUCCESS, it, null)
        }
    }

    fun getProgressState(): LiveData<Boolean> = progressState

}