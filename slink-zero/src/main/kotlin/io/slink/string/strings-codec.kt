package io.slink.string

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun String.encodeBase64(): String {
    return Base64.encode(this.toByteArray()).replace("=", "")
}

@OptIn(ExperimentalEncodingApi::class)
fun String.decodeBase64(): String {
    return String(Base64.decode(this))
}
