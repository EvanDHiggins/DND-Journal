package com.higgins.dndnotes.util

fun <T> Set<T>.toggle(t: T): Set<T> {
    if (this.contains(t)) {
        return this.minus(t)
    }
    return this.plus(t)
}