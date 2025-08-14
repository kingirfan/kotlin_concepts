package com.example.first_app.room_db.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.first_app.room_db.data.Graph
import com.example.first_app.room_db.data.Wish
import com.example.first_app.room_db.data.WishRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(private val wishRepository: WishRepository = Graph.wishRepository) :
    ViewModel() {

    var wishTitleState by mutableStateOf("")
    var wishDescritionState by mutableStateOf("")

    fun onWishTitleChanged(newString: String) {
        wishTitleState = newString
    }

    fun onWishDescritionChanged(newString: String) {
        wishDescritionState = newString
    }

    lateinit var getAllWishes: Flow<List<Wish>>

    init {
        viewModelScope.launch {
            getAllWishes = wishRepository.getAllWishes()
        }
    }

    fun addWish(wish: Wish) {
        viewModelScope.launch {
            wishRepository.addAWis(wish = wish)
        }
    }

    fun getWishById(id: Long): Flow<Wish> {
        return wishRepository.getWishById(id = id)
    }

    fun updateWish(wish: Wish) {
        viewModelScope.launch {
            wishRepository.updateWish(wish = wish)
        }
    }

    fun deleteWish(wish: Wish) {
        viewModelScope.launch {
            wishRepository.deleteWish(wish = wish)
        }
    }


}