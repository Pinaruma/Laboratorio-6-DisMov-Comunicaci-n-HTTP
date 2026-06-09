package com.example.lab_http_googleapis.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab_http_googleapis.data.model.BookItem
import com.example.lab_http_googleapis.databinding.ItemBookBinding

class BooksAdapter(
    private val onItemClick: (BookItem) -> Unit
) : ListAdapter<BookItem, BooksAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: BookItem) {
            binding.tvTitle.text = book.volumeInfo.title ?: "Sin título"
            binding.tvAuthor.text = book.volumeInfo.authors?.joinToString(", ") ?: "Autor desconocido"
            binding.tvDate.text = book.volumeInfo.publishedDate ?: "Fecha no disponible"

            Glide.with(binding.root.context)
                .load(book.volumeInfo.imageLinks?.thumbnail)
                .into(binding.ivCover)

            binding.root.setOnClickListener { onItemClick(book) }
        }
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<BookItem>() {
    override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean =
        oldItem == newItem
}
