package com.nevermore.base.ext

inline fun <reified T> Any?.isInstance(): Boolean {
    return this is T
}

inline fun <reified T> Any?.asInstance() = this as? T