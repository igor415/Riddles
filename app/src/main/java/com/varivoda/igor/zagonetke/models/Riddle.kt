package com.varivoda.igor.zagonetke.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Riddle(
    var id: Int,
    @SerializedName("riddle")
    var riddleText: String,
    var answer: String
): Parcelable