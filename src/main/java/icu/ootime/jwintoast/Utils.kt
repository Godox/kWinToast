package icu.ootime.jwintoast

inline fun <reified T> Iterable<Any>.firstOfTypeOrNull() = firstOrNull { it is T } as T?
