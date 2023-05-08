package io.slink.datetime

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle
import java.time.temporal.TemporalAccessor

fun String.toDateFormat(): DateTimeFormatter {
    return DateTimeFormatter.ofPattern(this).withResolverStyle(ResolverStyle.STRICT)
}

val dmyDash: DateTimeFormatter = "dd-MM-uuuu".toDateFormat()
val dmyDashLoose: DateTimeFormatter = "d-M-uuuu".toDateFormat()
val dmySlash: DateTimeFormatter = "dd/MM/uuuu".toDateFormat()
val dmySlashLoose: DateTimeFormatter = "d/M/uuuu".toDateFormat()

val ymdDash: DateTimeFormatter = "uuuu-MM-dd".toDateFormat()
val ymdDashLoose: DateTimeFormatter = "uuuu-M-d".toDateFormat()
val ymdSlash: DateTimeFormatter = "uuuu/MM/dd".toDateFormat()
val ymdSlashLoose: DateTimeFormatter = "uuuu/M/d".toDateFormat()

fun DateTimeFormatter.localDate(dateStr: String): LocalDate {
    return LocalDate.from(this.parse(dateStr))
}

fun DateTimeFormatter.validDate(dateStr: String): Boolean {
    return try {
        this.parse(dateStr)
        true
    } catch (e: DateTimeParseException) {
        false
    }
}

fun TemporalAccessor.formatYmdDash() = ymdDash.format(this)
fun TemporalAccessor.formatYmdSlash() = ymdSlash.format(this)
fun TemporalAccessor.formatDmyDash() = dmyDash.format(this)
fun TemporalAccessor.formatDmySlash() = dmySlash.format(this)

fun String.localDate(format: DateTimeFormatter): LocalDate {
    return format.localDate(this)
}