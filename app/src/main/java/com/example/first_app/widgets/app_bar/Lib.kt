package com.example.first_app.widgets.app_bar

import androidx.annotation.DrawableRes
import com.example.first_app.R

data class Lib(@DrawableRes val icon: Int, val name: String)

val libraries = listOf<Lib>(
    Lib(R.drawable.ic_subscribe, "first"),
    Lib(R.drawable.baseline_library_add_24, "second"),
    Lib(R.drawable.browse, "third"),
    Lib(R.drawable.baseline_person_add_alt_1_24, "forth"),
    Lib(R.drawable.ic_subscribe, "fifth"),
)
