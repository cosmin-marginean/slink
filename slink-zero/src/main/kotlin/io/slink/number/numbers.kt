package io.slink.number

import java.text.DecimalFormat
import java.text.NumberFormat

val NUMBER_FMT_GROUPED: ThreadLocal<NumberFormat> = ThreadLocal.withInitial { DecimalFormat("#,###") }
val NUMBER_FMT_2DECIMALS: ThreadLocal<NumberFormat> = ThreadLocal.withInitial { DecimalFormat("#,###.00") }

fun Number.format(format: ThreadLocal<NumberFormat>): String {
    return format.get().format(this)
}

fun Number.formatGrouped(): String = format(NUMBER_FMT_GROUPED)
fun Number.formatDecimals(): String = format(NUMBER_FMT_2DECIMALS)
fun Number.formatCurrency(): String = format(NUMBER_FMT_2DECIMALS)
