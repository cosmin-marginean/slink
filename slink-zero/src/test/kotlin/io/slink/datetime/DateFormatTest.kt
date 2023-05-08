package io.slink.datetime

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class DateFormatTest : StringSpec({

    "format date - dmyDash" {
        dmyDash.format(LocalDate.of(2021, 10, 23)) shouldBe "23-10-2021"
        dmyDash.format(LocalDate.of(2021, 5, 23)) shouldBe "23-05-2021"
        dmyDashLoose.format(LocalDate.of(2021, 5, 23)) shouldBe "23-5-2021"
    }

    "format date - dmySlash" {
        dmySlash.format(LocalDate.of(2021, 10, 23)) shouldBe "23/10/2021"
        dmySlash.format(LocalDate.of(2021, 5, 23)) shouldBe "23/05/2021"
        dmySlashLoose.format(LocalDate.of(2021, 5, 23)) shouldBe "23/5/2021"
    }

    "format date - ymdDash" {
        ymdDash.format(LocalDate.of(2021, 10, 23)) shouldBe "2021-10-23"
        ymdDash.format(LocalDate.of(2021, 10, 5)) shouldBe "2021-10-05"
        ymdDash.format(LocalDate.of(2021, 5, 23)) shouldBe "2021-05-23"
        ymdDashLoose.format(LocalDate.of(2021, 5, 23)) shouldBe "2021-5-23"
        ymdDashLoose.format(LocalDate.of(2021, 5, 9)) shouldBe "2021-5-9"
    }

    "format date - ymdSlash" {
        ymdSlash.format(LocalDate.of(2021, 10, 23)) shouldBe "2021/10/23"
        ymdSlash.format(LocalDate.of(2021, 10, 5)) shouldBe "2021/10/05"
        ymdSlash.format(LocalDate.of(2021, 5, 23)) shouldBe "2021/05/23"
        ymdSlashLoose.format(LocalDate.of(2021, 5, 23)) shouldBe "2021/5/23"
        ymdSlashLoose.format(LocalDate.of(2021, 5, 9)) shouldBe "2021/5/9"
    }
})