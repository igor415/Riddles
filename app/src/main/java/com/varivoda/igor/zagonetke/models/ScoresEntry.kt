package com.varivoda.igor.zagonetke.models

import com.google.gson.annotations.SerializedName

data class ScoresEntry(
    @SerializedName("username")
    var username: String,
    @SerializedName("question_number")
    var number: Int,
    @SerializedName("score")
    var score: Int
)