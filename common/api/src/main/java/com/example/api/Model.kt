package com.example.api

interface Model

fun Model.toDomain(): Model = this
fun Model.toSerializable(): Model = this