package com.example.quoteapp.VM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.quoteapp.Repo.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class QuoteVM @Inject constructor(private val repository: QuoteRepository) : ViewModel() {
    val list = repository.getQuotes().cachedIn(viewModelScope)
}