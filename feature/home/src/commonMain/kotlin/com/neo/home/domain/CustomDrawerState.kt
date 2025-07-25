package com.neo.home.domain

enum class CustomDrawerState {
    Opended, Closed
}

fun CustomDrawerState.isOpen() : Boolean {
    return this == CustomDrawerState.Opended
}

fun CustomDrawerState.opposite() : CustomDrawerState {
    return if (this == CustomDrawerState.Opended) CustomDrawerState.Closed else CustomDrawerState.Opended
}