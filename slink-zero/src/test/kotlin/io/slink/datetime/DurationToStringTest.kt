package io.slink.datetime

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class DurationToStringTest : StringSpec({

    "simple" {
        Duration.ofDays(16).toHumanReadableString() shouldBe "16d"
        Duration.ofHours(23).toHumanReadableString() shouldBe "23h"
        Duration.ofMinutes(43).toHumanReadableString() shouldBe "43m"
        Duration.ofSeconds(5).toHumanReadableString() shouldBe "5s"
        Duration.ofMillis(762).toHumanReadableString() shouldBe "762ms"
    }

    "multiple time units - positive" {
        Duration.ofDays(16).plus(Duration.ofHours(23)).toHumanReadableString() shouldBe "16d 23h"
        Duration.ofDays(16).plus(Duration.ofHours(23)).plus(Duration.ofMinutes(45)).toHumanReadableString() shouldBe "16d 23h 45m"
        Duration.ofDays(16).plus(Duration.ofHours(23)).plus(Duration.ofMinutes(45)).plus(Duration.ofSeconds(12)).toHumanReadableString() shouldBe
                "16d 23h 45m 12s"
        Duration.ofDays(16).plus(Duration.ofHours(23)).plus(Duration.ofMinutes(45))
                .plus(Duration.ofSeconds(12).plus(Duration.ofMillis(345))).toHumanReadableString() shouldBe
                "16d 23h 45m 12s 345ms"


        Duration.ofHours(23).plus(Duration.ofMinutes(45))
                .plus(Duration.ofSeconds(12).plus(Duration.ofMillis(345))).toHumanReadableString() shouldBe
                "23h 45m 12s 345ms"
        Duration.ofMinutes(45).plus(Duration.ofSeconds(12).plus(Duration.ofMillis(345))).toHumanReadableString() shouldBe "45m 12s 345ms"
        Duration.ofSeconds(12).plus(Duration.ofMillis(345)).toHumanReadableString() shouldBe "12s 345ms"
        Duration.ofMillis(345).toHumanReadableString() shouldBe "345ms"
    }

    "kotlin Duration" {
        16.toDuration(DurationUnit.DAYS).plus(23.toDuration(DurationUnit.HOURS)).toHumanReadableString() shouldBe "16d 23h"
    }

    "multiple time units - negative" {
        Duration.ofDays(-16).plus(Duration.ofHours(-23)).toHumanReadableString() shouldBe "-16d 23h"
        Duration.ofDays(-16).plus(Duration.ofHours(-23)).plus(Duration.ofMinutes(-45)).toHumanReadableString() shouldBe "-16d 23h 45m"
        Duration.ofDays(-16).plus(Duration.ofHours(-23)).plus(Duration.ofMinutes(-45)).plus(Duration.ofSeconds(-12)).toHumanReadableString() shouldBe
                "-16d 23h 45m 12s"
        Duration.ofDays(-16).plus(Duration.ofHours(-23)).plus(Duration.ofMinutes(-45))
                .plus(Duration.ofSeconds(-12).plus(Duration.ofMillis(-345))).toHumanReadableString() shouldBe
                "-16d 23h 45m 12s 345ms"


        Duration.ofHours(-23).plus(Duration.ofMinutes(-45))
                .plus(Duration.ofSeconds(-12).plus(Duration.ofMillis(-345))).toHumanReadableString() shouldBe
                "-23h 45m 12s 345ms"
        Duration.ofMinutes(-45).plus(Duration.ofSeconds(-12).plus(Duration.ofMillis(-345))).toHumanReadableString() shouldBe "-45m 12s 345ms"
        Duration.ofSeconds(-12).plus(Duration.ofMillis(-345)).toHumanReadableString() shouldBe "-12s 345ms"
        Duration.ofMillis(-345).toHumanReadableString() shouldBe "-345ms"
    }

    "print zeros - positive" {
        Duration.ofDays(16).plus(Duration.ofMinutes(45)).plus(Duration.ofSeconds(12).plus(Duration.ofMillis(345))).toHumanReadableString() shouldBe
                "16d 0h 45m 12s 345ms"
        Duration.ofDays(16).plus(Duration.ofSeconds(12).plus(Duration.ofMillis(345))).toHumanReadableString() shouldBe
                "16d 0h 0m 12s 345ms"
        Duration.ofDays(16).plus(Duration.ofMillis(345)).toHumanReadableString() shouldBe
                "16d 0h 0m 0s 345ms"
        Duration.ofHours(16).plus(Duration.ofSeconds(27)).toHumanReadableString() shouldBe
                "16h 0m 27s"
    }

    "print zeros - negative" {
        Duration.ofDays(-16).plus(Duration.ofMinutes(-45)).plus(Duration.ofSeconds(-12).plus(Duration.ofMillis(-345))).toHumanReadableString() shouldBe
                "-16d 0h 45m 12s 345ms"
        Duration.ofDays(-16).plus(Duration.ofSeconds(-12).plus(Duration.ofMillis(-345))).toHumanReadableString() shouldBe
                "-16d 0h 0m 12s 345ms"
        Duration.ofDays(-16).plus(Duration.ofMillis(-345)).toHumanReadableString() shouldBe
                "-16d 0h 0m 0s 345ms"
        Duration.ofHours(-16).plus(Duration.ofSeconds(-27)).toHumanReadableString() shouldBe
                "-16h 0m 27s"
    }

    "symmetry - positive" {
        "16d".toDuration()!!.toHumanReadableString() shouldBe "16d"
        "16d 5h".toDuration()!!.toHumanReadableString() shouldBe "16d 5h"
        "16d 5h 23m".toDuration()!!.toHumanReadableString() shouldBe "16d 5h 23m"
        "16d 5h 23m 45s".toDuration()!!.toHumanReadableString() shouldBe "16d 5h 23m 45s"
        "16d 5h 23m 45s 124ms".toDuration()!!.toHumanReadableString() shouldBe "16d 5h 23m 45s 124ms"
        "16d 0h 23m 45s 124ms".toDuration()!!.toHumanReadableString() shouldBe "16d 0h 23m 45s 124ms"
        "16d 0h 23m 45s 0ms".toDuration()!!.toHumanReadableString() shouldBe "16d 0h 23m 45s"
        "16d 0h 23m 0s 0ms".toDuration()!!.toHumanReadableString() shouldBe "16d 0h 23m"
        "16d 0h 0m 0s 0ms".toDuration()!!.toHumanReadableString() shouldBe "16d"
        "124ms".toDuration()!!.toHumanReadableString() shouldBe "124ms"
        "45m 124ms".toDuration()!!.toHumanReadableString() shouldBe "45m 0s 124ms"
        "29s 124ms".toDuration()!!.toHumanReadableString() shouldBe "29s 124ms"

        "24h".toDuration()!!.toHumanReadableString() shouldBe "1d"
        "3600s".toDuration()!!.toHumanReadableString() shouldBe "1h"
        "7200000ms".toDuration()!!.toHumanReadableString() shouldBe "2h"
        "7200m".toDuration()!!.toHumanReadableString() shouldBe "5d"
    }

    "symmetry - negative" {
        "-16d".toDuration()!!.toHumanReadableString() shouldBe "-16d"
        "-16d 5h".toDuration()!!.toHumanReadableString() shouldBe "-16d 5h"
        "-16d 5h 23m".toDuration()!!.toHumanReadableString() shouldBe "-16d 5h 23m"
        "-16d 5h 23m 45s".toDuration()!!.toHumanReadableString() shouldBe "-16d 5h 23m 45s"
        "-16d 5h 23m 45s 124ms".toDuration()!!.toHumanReadableString() shouldBe "-16d 5h 23m 45s 124ms"
        "-16d 0h 23m 45s 124ms".toDuration()!!.toHumanReadableString() shouldBe "-16d 0h 23m 45s 124ms"
        "-16d 0h 23m 45s 0ms".toDuration()!!.toHumanReadableString() shouldBe "-16d 0h 23m 45s"
        "-16d 0h 23m 0s 0ms".toDuration()!!.toHumanReadableString() shouldBe "-16d 0h 23m"
        "-16d 0h 0m 0s 0ms".toDuration()!!.toHumanReadableString() shouldBe "-16d"
        "-124ms".toDuration()!!.toHumanReadableString() shouldBe "-124ms"
        "-45m 124ms".toDuration()!!.toHumanReadableString() shouldBe "-45m 0s 124ms"
        "-29s 124ms".toDuration()!!.toHumanReadableString() shouldBe "-29s 124ms"

        "-24h".toDuration()!!.toHumanReadableString() shouldBe "-1d"
        "-3600s".toDuration()!!.toHumanReadableString() shouldBe "-1h"
        "-7200000ms".toDuration()!!.toHumanReadableString() shouldBe "-2h"
        "-7200m".toDuration()!!.toHumanReadableString() shouldBe "-5d"
    }
})