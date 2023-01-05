package com.example.quoteapp.Paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteapp.Models.Result
import com.example.quoteapp.databinding.QuoteItemBinding

class QuotePagingAdapter : PagingDataAdapter<Result, QuotePagingAdapter.QuoteViewHolder>(object :
    DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}) {

    class QuoteViewHolder(itemView: QuoteItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        val quote = itemView.quote
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.quote.text = item.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = QuoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding)
    }

}