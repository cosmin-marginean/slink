package io.slink.datetime

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.Duration
import kotlin.time.toKotlinDuration

class DurationParseTest : StringSpec({

    "regex match" {
        REGEX_DURATION.matches("24d") shouldBe true
        REGEX_DURATION.matches("-24d") shouldBe true
        REGEX_DURATION.matches("45m") shouldBe true
        REGEX_DURATION.matches("-45m") shouldBe true
        REGEX_DURATION.matches("3h") shouldBe true
        REGEX_DURATION.matches("56s") shouldBe true
        REGEX_DURATION.matches("345ms") shouldBe true

        REGEX_DURATION.matches("024d") shouldBe true
        REGEX_DURATION.matches("045m") shouldBe true
        REGEX_DURATION.matches("03h") shouldBe true
        REGEX_DURATION.matches("056s") shouldBe true
        REGEX_DURATION.matches("0345ms") shouldBe true
        REGEX_DURATION.matches("-0345ms") shouldBe true

        REGEX_DURATION.matches("24d 3h") shouldBe true
        REGEX_DURATION.matches("24d 3h 45m") shouldBe true
        REGEX_DURATION.matches("24d 3h 45m 56s") shouldBe true
        REGEX_DURATION.matches("24d 3h 45m 56s 345ms") shouldBe true
        REGEX_DURATION.matches("345ms") shouldBe true
        REGEX_DURATION.matches("56s 345ms") shouldBe true
        REGEX_DURATION.matches("45m 56s 345ms") shouldBe true
        REGEX_DURATION.matches("3h 45m 56s 345ms") shouldBe true
        REGEX_DURATION.matches("3h 45m 56s 999ms") shouldBe true
        REGEX_DURATION.matches("3h 45m 59s 999ms") shouldBe true
        REGEX_DURATION.matches("3h 59m 59s 999ms") shouldBe true
        REGEX_DURATION.matches("-3h 59m 59s 999ms") shouldBe true

        REGEX_DURATION.matches("024d 03h") shouldBe true
        REGEX_DURATION.matches("024d 03h 045m") shouldBe true
        REGEX_DURATION.matches("24d 03h 45m 56s") shouldBe true
        REGEX_DURATION.matches("24d 3h 045m 056s 0345ms") shouldBe true
        REGEX_DURATION.matches("0345ms") shouldBe true
        REGEX_DURATION.matches("56s 345ms") shouldBe true
        REGEX_DURATION.matches("045m 56s 345ms") shouldBe true
        REGEX_DURATION.matches("03h 45m 56s 345ms") shouldBe true
        REGEX_DURATION.matches("03h 45m 056s 999ms") shouldBe true
        REGEX_DURATION.matches("-03h 45m 056s 999ms") shouldBe true
        REGEX_DURATION.matches("03h 045m 059s 999ms") shouldBe true
        REGEX_DURATION.matches("03h 059m 59s 999ms") shouldBe true
        REGEX_DURATION.matches("24d 03h 059m 59s 999ms") shouldBe true
        REGEX_DURATION.matches("24d 03h 0m 0s 0ms") shouldBe true
        REGEX_DURATION.matches("-24d 03h 0m 0s 0ms") shouldBe true

        REGEX_DURATION.matches("98723h") shouldBe true
        REGEX_DURATION.matches("29123m") shouldBe true
        REGEX_DURATION.matches("165s") shouldBe true
        REGEX_DURATION.matches("2397165ms") shouldBe true
        REGEX_DURATION.matches("-2397165ms") shouldBe true
        REGEX_DURATION.matches("24h") shouldBe true
        REGEX_DURATION.matches("60m") shouldBe true
        REGEX_DURATION.matches("1000s") shouldBe true
        REGEX_DURATION.matches("-1000s") shouldBe true

        REGEX_DURATION.matches("0098723h") shouldBe true
        REGEX_DURATION.matches("-0098723h") shouldBe true
        REGEX_DURATION.matches("029123m") shouldBe true
        REGEX_DURATION.matches("00165s") shouldBe true
        REGEX_DURATION.matches("002397165ms") shouldBe true
        REGEX_DURATION.matches("0024h") shouldBe true
        REGEX_DURATION.matches("060m") shouldBe true
        REGEX_DURATION.matches("01000s") shouldBe true

        REGEX_DURATION.matches("29123m 45s") shouldBe false
        REGEX_DURATION.matches("98723h 45s") shouldBe false
        REGEX_DURATION.matches("98723h 9212m 45s") shouldBe false
        REGEX_DURATION.matches("24d 3h 45m 156s") shouldBe false
        REGEX_DURATION.matches("24d 3h 45m 4s 9328742ms") shouldBe false

        REGEX_DURATION.matches("65d 24h") shouldBe false
        REGEX_DURATION.matches("65d 24h 60m") shouldBe false
        REGEX_DURATION.matches("65d 24h 60m 60s") shouldBe false
        REGEX_DURATION.matches("5m 60s") shouldBe false
        REGEX_DURATION.matches("5m 1000s") shouldBe false
    }

    "parse positive duration" {
        "16d".toDuration() shouldBe Duration.ofDays(16).toKotlinDuration()
        "016d".toDuration() shouldBe Duration.ofDays(16).toKotlinDuration()
        "4h".toDuration() shouldBe Duration.ofHours(4).toKotlinDuration()
        "04h".toDuration() shouldBe Duration.ofHours(4).toKotlinDuration()
        "94h".toDuration() shouldBe Duration.ofHours(94).toKotlinDuration()
        "094h".toDuration() shouldBe Duration.ofHours(94).toKotlinDuration()
        "0094h".toDuration() shouldBe Duration.ofHours(94).toKotlinDuration()
        "5m".toDuration() shouldBe Duration.ofMinutes(5).toKotlinDuration()
        "05m".toDuration() shouldBe Duration.ofMinutes(5).toKotlinDuration()
        "0155m".toDuration() shouldBe Duration.ofMinutes(155).toKotlinDuration()
        "2374s".toDuration() shouldBe Duration.ofSeconds(2374).toKotlinDuration()
        "489ms".toDuration() shouldBe Duration.ofMillis(489).toKotlinDuration()
        "324489ms".toDuration() shouldBe Duration.ofMillis(324489).toKotlinDuration()
        "12h 0m 3s".toDuration() shouldBe Duration.ofHours(12).plus(Duration.ofSeconds(3)).toKotlinDuration()
        "12h 3s".toDuration() shouldBe Duration.ofHours(12).plus(Duration.ofSeconds(3)).toKotlinDuration()
        "16d 4h".toDuration() shouldBe Duration.ofDays(16).plus(Duration.ofHours(4)).toKotlinDuration()
        "16d 04h".toDuration() shouldBe Duration.ofDays(16).plus(Duration.ofHours(4)).toKotlinDuration()
        "16d 4h 35s".toDuration() shouldBe Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).toKotlinDuration()
        "16d 04h 035s".toDuration() shouldBe Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).toKotlinDuration()
        "16d 4h 35s 782ms".toDuration() shouldBe Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).plus(Duration.ofMillis(782)).toKotlinDuration()
        "16d 4h 35s 0782ms".toDuration() shouldBe Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).plus(Duration.ofMillis(782)).toKotlinDuration()
        "16d 04h 035s 0782ms".toDuration() shouldBe Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).plus(Duration.ofMillis(782)).toKotlinDuration()
        "16d 04h 035s 0782ms".toDuration() shouldBe Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).plus(Duration.ofMillis(782)).toKotlinDuration()
        "0016d 04h 035s 0782ms".toDuration() shouldBe Duration.ofDays(16).plus(Duration.ofHours(4)).plus(Duration.ofSeconds(35)).plus(Duration.ofMillis(782)).toKotlinDuration()
        "13s 923ms".toDuration() shouldBe Duration.ofSeconds(13).plus(Duration.ofMillis(923)).toKotlinDuration()
        "56m 13s 923ms".toDuration() shouldBe Duration.ofMinutes(56).plus(Duration.ofSeconds(13)).plus(Duration.ofMillis(923)).toKotlinDuration()
        "056m 013s 00923ms".toDuration() shouldBe Duration.ofMinutes(56).plus(Duration.ofSeconds(13)).plus(Duration.ofMillis(923)).toKotlinDuration()
    }

    "parse negative duration" {
        "-16d".toDuration() shouldBe Duration.ofDays(-16).toKotlinDuration()
        "-016d".toDuration() shouldBe Duration.ofDays(-16).toKotlinDuration()
        "-4h".toDuration() shouldBe Duration.ofHours(-4).toKotlinDuration()
        "-04h".toDuration() shouldBe Duration.ofHours(-4).toKotlinDuration()
        "-94h".toDuration() shouldBe Duration.ofHours(-94).toKotlinDuration()
        "-094h".toDuration() shouldBe Duration.ofHours(-94).toKotlinDuration()
        "-0094h".toDuration() shouldBe Duration.ofHours(-94).toKotlinDuration()
        "-5m".toDuration() shouldBe Duration.ofMinutes(-5).toKotlinDuration()
        "-05m".toDuration() shouldBe Duration.ofMinutes(-5).toKotlinDuration()
        "-0155m".toDuration() shouldBe Duration.ofMinutes(-155).toKotlinDuration()
        "-2374s".toDuration() shouldBe Duration.ofSeconds(-2374).toKotlinDuration()
        "-489ms".toDuration() shouldBe Duration.ofMillis(-489).toKotlinDuration()
        "-324489ms".toDuration() shouldBe Duration.ofMillis(-324489).toKotlinDuration()
        "-12h 0m 3s".toDuration() shouldBe Duration.ofHours(-12).plus(Duration.ofSeconds(-3)).toKotlinDuration()
        "-12h 3s".toDuration() shouldBe Duration.ofHours(-12).plus(Duration.ofSeconds(-3)).toKotlinDuration()
        "-16d 4h".toDuration() shouldBe Duration.ofDays(-16).plus(Duration.ofHours(-4)).toKotlinDuration()
        "-16d 04h".toDuration() shouldBe Duration.ofDays(-16).plus(Duration.ofHours(-4)).toKotlinDuration()
        "-16d 4h 35s".toDuration() shouldBe Duration.ofDays(-16).plus(Duration.ofHours(-4)).plus(Duration.ofSeconds(-35)).toKotlinDuration()
        "-16d 04h 035s".toDuration() shouldBe Duration.ofDays(-16).plus(Duration.ofHours(-4)).plus(Duration.ofSeconds(-35)).toKotlinDuration()
        "-16d 4h 35s 782ms".toDuration() shouldBe Duration.ofDays(-16).plus(Duration.ofHours(-4)).plus(Duration.ofSeconds(-35)).plus(Duration.ofMillis(-782)).toKotlinDuration()
        "-16d 4h 35s 0782ms".toDuration() shouldBe Duration.ofDays(-16).plus(Duration.ofHours(-4)).plus(Duration.ofSeconds(-35)).plus(Duration.ofMillis(-782)).toKotlinDuration()
        "-16d 04h 035s 0782ms".toDuration() shouldBe Duration.ofDays(-16).plus(Duration.ofHours(-4)).plus(Duration.ofSeconds(-35)).plus(Duration.ofMillis(-782)).toKotlinDuration()
        "-16d 04h 035s 0782ms".toDuration() shouldBe Duration.ofDays(-16).plus(Duration.ofHours(-4)).plus(Duration.ofSeconds(-35)).plus(Duration.ofMillis(-782)).toKotlinDuration()
        "-0016d 04h 035s 0782ms".toDuration() shouldBe Duration.ofDays(-16).plus(Duration.ofHours(-4)).plus(Duration.ofSeconds(-35)).plus(Duration.ofMillis(-782)).toKotlinDuration()
        "-13s 923ms".toDuration() shouldBe Duration.ofSeconds(-13).plus(Duration.ofMillis(-923)).toKotlinDuration()
        "-56m 13s 923ms".toDuration() shouldBe Duration.ofMinutes(-56).plus(Duration.ofSeconds(-13)).plus(Duration.ofMillis(-923)).toKotlinDuration()
        "-056m 013s 00923ms".toDuration() shouldBe Duration.ofMinutes(-56).plus(Duration.ofSeconds(-13)).plus(Duration.ofMillis(-923)).toKotlinDuration()
    }

    "exact values" {
        "5d".toDuration() shouldBe Duration.ofDays(5).toKotlinDuration()
        "5d 0h".toDuration() shouldBe Duration.ofDays(5).toKotlinDuration()
        "5d 0h 0m".toDuration() shouldBe Duration.ofDays(5).toKotlinDuration()
        "5d 0h 0m 0s".toDuration() shouldBe Duration.ofDays(5).toKotlinDuration()
        "5d 0h 0m 0s 0ms".toDuration() shouldBe Duration.ofDays(5).toKotlinDuration()
        "5d 0h 0m 0ms".toDuration() shouldBe Duration.ofDays(5).toKotlinDuration()
        "5d 0m 0ms".toDuration() shouldBe Duration.ofDays(5).toKotlinDuration()
        "5d 0h 0m".toDuration() shouldBe Duration.ofDays(5).toKotlinDuration()

        "12h".toDuration() shouldBe Duration.ofHours(12).toKotlinDuration()
        "12h 0m".toDuration() shouldBe Duration.ofHours(12).toKotlinDuration()
        "12h 0s".toDuration() shouldBe Duration.ofHours(12).toKotlinDuration()
        "12h 00m 0s".toDuration() shouldBe Duration.ofHours(12).toKotlinDuration()
        "12h 00m 0s 000ms".toDuration() shouldBe Duration.ofHours(12).toKotlinDuration()

        "0d".toDuration() shouldBe Duration.ofNanos(0).toKotlinDuration()
        "0d 0h".toDuration() shouldBe Duration.ofNanos(0).toKotlinDuration()
        "0d 0h 0m".toDuration() shouldBe Duration.ofNanos(0).toKotlinDuration()
        "0d 0h 0m 0s".toDuration() shouldBe Duration.ofNanos(0).toKotlinDuration()
        "0d 0h 0m 0s 0ms".toDuration() shouldBe Duration.ofNanos(0).toKotlinDuration()
        "0ms".toDuration() shouldBe Duration.ofNanos(0).toKotlinDuration()
        "0s".toDuration() shouldBe Duration.ofNanos(0).toKotlinDuration()
        "0m".toDuration() shouldBe Duration.ofNanos(0).toKotlinDuration()
        "0h".toDuration() shouldBe Duration.ofNanos(0).toKotlinDuration()
    }

    "invalid duration" {
        "test".toDuration() shouldBe null
        "16hh".toDuration() shouldBe null
        "123dd".toDuration() shouldBe null
        "435mm".toDuration() shouldBe null
        "923ss".toDuration() shouldBe null
        "34".toDuration() shouldBe null
        "34 d".toDuration() shouldBe null
        "92103 h".toDuration() shouldBe null
        "2109382".toDuration() shouldBe null
    }
})