package com.example.first_app.room_db

import android.app.Application
import com.example.first_app.room_db.data.Graph

class WishListApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}