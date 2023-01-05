package com.example.quoteapp.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quoteapp.Paging.LoaderAdapter
import com.example.quoteapp.Paging.QuotePagingAdapter
import com.example.quoteapp.R
import com.example.quoteapp.VM.QuoteVM
import com.example.quoteapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var quoteViewModel: QuoteVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        quoteViewModel = ViewModelProvider(this)[QuoteVM::class.java]

        val adapter = QuotePagingAdapter()

        binding.quoteList.layoutManager = LinearLayoutManager(this)
        binding.quoteList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        quoteViewModel.list.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
}