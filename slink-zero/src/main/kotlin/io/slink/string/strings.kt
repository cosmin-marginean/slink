package io.slink.string

import java.net.URLEncoder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.regex.Pattern

val REGEX_NON_ALPHA = "[^a-zA-Z\\d]+".toRegex()
val REGEX_WHITESPACE = "(\u200B|\\s)+".toRegex()

fun String.urlEncode(charset: Charset = StandardCharsets.UTF_8): String {
    return URLEncoder.encode(this, charset.name())
}

fun String.cleanWhitespace(): String {
    return replace(REGEX_WHITESPACE, " ").trim()
}

fun String.removeWhitespace(): String {
    return replace(REGEX_WHITESPACE, "").trim()
}

fun String.titleCaseFirstChar(locale: Locale = Locale.getDefault()): String {
    return replaceFirstChar { it.titlecase(locale) }
}

fun String.cleanEmail(): String {
    return cleanWhitespace().lowercase()
}

fun String.ellipsis(maxLength: Int): String {
    return if (this.length > maxLength) {
        this.take(maxLength - 3) + "..."
    } else {
        this
    }
}

fun String?.nullIfBlank(): String? {
    return if (this != null && this.isBlank()) {
        null
    } else {
        this
    }
}

fun String.anonymizeEmail(visibleEndChars: Int = 0): String {
    val elements = this.split("@")
    return elements[0].anonymize(visibleEndChars) + "@" + elements[1].anonymize(visibleEndChars)
}

fun String.anonymize(visibleEndChars: Int = 0): String {
    val visibleStartIndex = (this.length - visibleEndChars).coerceAtLeast(0)
    return String(this
            .toCharArray()
            .mapIndexed { index, char ->
                if (index < visibleStartIndex) '*' else char
            }
            .toCharArray()
    )
}

private val DOMAIN_COLON_SLASHES = ".*://".toRegex()
private val DOMAIN_PORT = ":.*".toRegex()
private val DOMAIN_REST_OF_PATH = "/.*".toRegex()
fun String.domain(): String {
    return this
            .lowercase()
            .replace(DOMAIN_COLON_SLASHES, "")
            .replace(DOMAIN_PORT, "")
            .replace(DOMAIN_REST_OF_PATH, "")
}

fun String.toLines(): List<String> {
    return this.split("\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
}

fun <E : Enum<E>> Enum<E>.enumLabel(): String {
    return this.name.enumLabel()
}

fun String.enumLabel(): String {
    return this.replace("_", " ").lowercase().titleCaseFirstChar()
}

private val PATTERN_KEBAB = Pattern.compile("-([a-z])")
private val REGEX_CAMEL = "([a-z0-9])([A-Z])".toRegex()

fun String.camelToKebabCase(): String {
    return this.replace(REGEX_CAMEL, "$1-$2").lowercase()
}

fun String.kebabToCamelCase(): String {
    return PATTERN_KEBAB
            .matcher(this)
            .replaceAll { mr -> mr.group(1).uppercase() }
}
