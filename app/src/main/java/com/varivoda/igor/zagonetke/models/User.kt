package com.varivoda.igor.zagonetke.models

data class User(
    var username: String,
    var first_name: String,
    var last_name: String,
    var security: String,
    var email: String,
    var password: String
)