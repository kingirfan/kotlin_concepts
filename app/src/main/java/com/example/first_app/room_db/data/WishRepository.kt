package com.example.first_app.room_db.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {

    suspend fun addAWis(wish: Wish) {
        wishDao.addWish(wish)
    }

    fun getAllWishes(): Flow<List<Wish>> = wishDao.getAllWishes()

    fun getWishById(id: Long): Flow<Wish> = wishDao.getWishById(id)

    suspend fun updateWish(wish: Wish) {
        wishDao.updateWish(wish)
    }

    suspend fun deleteWish(wish: Wish) {
        wishDao.deleteWish(wish)
    }
}