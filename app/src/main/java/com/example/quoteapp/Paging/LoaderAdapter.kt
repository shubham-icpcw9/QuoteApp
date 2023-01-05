package com.example.quoteapp.Paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteapp.databinding.LoaderStateBinding

class LoaderAdapter : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(itemView : LoaderStateBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val loader = itemView.progressBar

        fun isLoaderVisible(loadState: LoadState) {
            loader.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.isLoaderVisible(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val binding = LoaderStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoaderViewHolder(binding)
    }
}