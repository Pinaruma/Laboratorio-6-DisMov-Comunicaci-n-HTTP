package com.example.lab_http_googleapis.ui

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_http_googleapis.data.model.BookItem
import com.example.lab_http_googleapis.data.repository.BooksRepository
import com.example.lab_http_googleapis.ui.UiState.Error
import kotlinx.coroutines.launch
import java.io.IOException

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val books: List<BookItem>) : UiState()
    data class Error(val message: String) : UiState()
}

class BooksViewModel : ViewModel() {
    private val repository = BooksRepository()
    private val _state = MutableLiveData<UiState>(UiState.Idle)
    val state: LiveData<UiState> = _state

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun search(query: String) {
        if (query.isBlank()) {
            _state.value = Error("Escribe un término de búsqueda")
            return
        }
        _state.value = UiState.Loading
        viewModelScope.launch {
            try {
                val books = repository.searchBooks(query.trim())
                _state.value = if (books.isEmpty())
                    Error("No se encontraron resultados")
                else UiState.Success(books)
            } catch (e: IOException) {
                _state.value = Error("Sin conexión a Internet")
            } catch (e: HttpException) {
                _state.value = Error("Error HTTP ${e.hashCode()}")
            } catch (e: Exception) {
                _state.value = Error("Error: ${e.localizedMessage}")
            }
        }
    }
}
