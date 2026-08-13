package com.example.naturuserinterface.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class AffirmationType{
    text,
    image,
    all
}


data class Affirmation(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    val type: AffirmationType
)


