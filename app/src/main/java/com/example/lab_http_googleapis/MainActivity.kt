package com.example.lab_http_googleapis

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_http_googleapis.data.model.BookItem
import com.example.lab_http_googleapis.ui.BooksAdapter
import com.example.lab_http_googleapis.ui.BooksViewModel
import com.example.lab_http_googleapis.ui.UiState

class MainActivity : AppCompatActivity() {

    private val viewModel: BooksViewModel by viewModels()
    private lateinit var adapter: BooksAdapter

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etQuery = findViewById<EditText>(R.id.etQuery)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val rv = findViewById<RecyclerView>(R.id.rvBooks)

        adapter = BooksAdapter { /* onClick */ }
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        btnSearch.setOnClickListener {
            viewModel.search(etQuery.text.toString())
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                is UiState.Success -> adapter.submitList(state.books)
                is UiState.Error -> Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                else -> { /* idle / loading */ }
            }
        }
    }

}
