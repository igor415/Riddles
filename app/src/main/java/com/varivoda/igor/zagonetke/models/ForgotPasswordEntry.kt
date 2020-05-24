package com.varivoda.igor.zagonetke.models

data class ForgotPasswordEntry(
    var username: String,
    var security: String,
    var password: String
)