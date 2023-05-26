package io.slink.datetime

import kotlin.math.absoluteValue
import kotlin.time.*

internal val REGEX_DURATION = """
    ^-?(
       (
         ((?<days>\d+)d)?(\s*)
         ((?<hours>(0*(2[0-3]|[0-1]?[0-9])))h)?(\s*)
         ((?<minutes>(0*([0-5]?[0-9])))m)?(\s*)
         ((?<seconds>(0*([0-5]?[0-9])))s)?(\s*)
         ((?<milliseconds>(0*([0-9]|[1-9][0-9]|[1-9][0-9][0-9])))ms)?
      )
    | (
         ((?<days2>\d+)d)
        |((?<hours2>\d+)h)
        |((?<minutes2>\d+)m)
        |((?<seconds2>\d+)s)
        |((?<milliseconds2>\d+)ms)
      )
    )$
""".replace("\\s+".toRegex(), "")
        .trim()
        .toRegex()

internal const val MS = 1L
internal const val SEC = 1000 * MS
internal const val MIN = 60 * SEC
internal const val HOUR = 60 * MIN
internal const val DAY = 24 * HOUR

internal val GROUP_UNIT_CONVERTERS = mapOf(
        "days" to DAY,
        "hours" to HOUR,
        "minutes" to MIN,
        "seconds" to SEC,
        "milliseconds" to MS,
        "days2" to DAY,
        "hours2" to HOUR,
        "minutes2" to MIN,
        "seconds2" to SEC,
        "milliseconds2" to MS
)

fun String.toJavaDuration(): java.time.Duration? {
    return this.toDuration()?.toJavaDuration()
}

fun String.toDuration(): Duration? {
    val matcher = REGEX_DURATION.find(this) ?: return null
    var durationMs = 0L

    GROUP_UNIT_CONVERTERS.forEach { (groupName, multiplier) ->
        val amount = (matcher.groups as MatchNamedGroupCollection)[groupName]?.value?.toLong()
        if (amount != null)
            durationMs += amount * multiplier
    }
    if (this.startsWith('-')) {
        durationMs = -durationMs
    }
    return durationMs.toDuration(DurationUnit.MILLISECONDS)
}

fun java.time.Duration.humanReadableString(): String {
    return this.toKotlinDuration().humanReadableString()
}

fun Duration.humanReadableString(): String {
    val millisValue = this.inWholeMilliseconds.absoluteValue
    if (millisValue == 0L) {
        return "0ms"
    }

    val days = millisValue / DAY
    var remainderD = millisValue - days * DAY

    val hours = remainderD / HOUR
    var remainderH = remainderD - hours * HOUR

    val minutes = remainderH / MIN
    val remainderM = remainderH - minutes * MIN

    val seconds = remainderM / SEC
    val remainderS = remainderM - seconds * SEC

    val milliseconds = remainderS / MS
    val elements = listOf("${days}d", "${hours}h", "${minutes}m", "${seconds}s", "${milliseconds}ms")
    val start = elements.indexOfFirst { it[0] != '0' }
    val end = elements.indexOfLast { it[0] != '0' }
    val string = elements.subList(start, end + 1).joinToString(" ")
    return if (this.isNegative()) {
        "-$string"
    } else {
        string
    }
}