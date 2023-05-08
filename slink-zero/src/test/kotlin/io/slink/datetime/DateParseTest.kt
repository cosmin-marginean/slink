package io.slink.datetime

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate


class DateParseTest : StringSpec({

    "parse date - dmySlash" {
        dmySlash.localDate("23/10/2021") shouldBe LocalDate.of(2021, 10, 23)
        dmySlashLoose.localDate("23/5/2021") shouldBe LocalDate.of(2021, 5, 23)
        dmySlashLoose.localDate("2/5/2021") shouldBe LocalDate.of(2021, 5, 2)
    }

    "parse date - dmyDash" {
        dmyDash.localDate("23-10-2021") shouldBe LocalDate.of(2021, 10, 23)
        dmyDashLoose.localDate("23-5-2021") shouldBe LocalDate.of(2021, 5, 23)
        dmyDashLoose.localDate("2-5-2021") shouldBe LocalDate.of(2021, 5, 2)
    }

    "parse date - ymdSlash" {
        ymdSlash.localDate("2021/10/23") shouldBe LocalDate.of(2021, 10, 23)
        ymdSlashLoose.localDate("2021/5/23") shouldBe LocalDate.of(2021, 5, 23)
        ymdSlashLoose.localDate("2021/5/2") shouldBe LocalDate.of(2021, 5, 2)
    }

    "parse date - ymdDash" {
        ymdDash.localDate("2021-10-23") shouldBe LocalDate.of(2021, 10, 23)
        ymdDashLoose.localDate("2021-5-23") shouldBe LocalDate.of(2021, 5, 23)
        ymdDashLoose.localDate("2021-5-2") shouldBe LocalDate.of(2021, 5, 2)
    }

    "valid date" {
        dmyDash.validDate("23-10-2021") shouldBe true
        dmyDash.validDate("23-1-2021") shouldBe false
        dmyDashLoose.validDate("23-1-2021") shouldBe true

        dmySlash.validDate("23/10/2021") shouldBe true
        dmySlash.validDate("23/1/2021") shouldBe false
        dmySlashLoose.validDate("23/1/2021") shouldBe true

        ymdDash.validDate("2021-10-23") shouldBe true
        ymdDash.validDate("2021-1-23") shouldBe false
        ymdDashLoose.validDate("2021-1-23") shouldBe true

        ymdSlash.validDate("2021/10/23") shouldBe true
        ymdSlash.validDate("2021/1/23") shouldBe false
        ymdSlashLoose.validDate("2021/1/23") shouldBe true
    }
})

//    @Test
//    fun zonedDateTime() {
//        val format1 = ZonedDateTime.from(LocalDateTime.of(2022, 10, 23, 10, 30, 23).atZone(ZoneId.of("GMT")))
//            .format(DateFormats.ZONED_DMY_SLASH_MINUTES)
//        assertEquals(format1, "23/10/2022 10:30 GMT")
//
//        val format2 = ZonedDateTime.from(LocalDateTime.of(2022, 10, 23, 10, 30, 23).atZone(ZoneId.of("GMT")))
//            .format(DateFormats.ZONED_DMY_SLASH)
//        assertEquals(format2, "23/10/2022 10:30:23 GMT")
//    }
//

