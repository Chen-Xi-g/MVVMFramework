package com.alvin.mvvm_framework.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tag(
    val name: String? = "",
    val url: String? = ""
) : Parcelable