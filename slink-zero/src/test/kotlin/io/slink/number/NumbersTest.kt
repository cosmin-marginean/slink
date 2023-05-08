package io.slink.number

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class NumbersTest : StringSpec({

    "grouped" {
        1.formatGrouped() shouldBe "1"
        23.formatGrouped() shouldBe "23"
        934.formatGrouped() shouldBe "934"
        6789.formatGrouped() shouldBe "6,789"
    }

    "2 decimals" {
        1.format(NUMBER_FMT_2DECIMALS) shouldBe "1.00"
        23.format(NUMBER_FMT_2DECIMALS) shouldBe "23.00"
        934.format(NUMBER_FMT_2DECIMALS) shouldBe "934.00"
        6789.format(NUMBER_FMT_2DECIMALS) shouldBe "6,789.00"

        1.formatDecimals() shouldBe "1.00"
        23.formatDecimals() shouldBe "23.00"
        934.formatDecimals() shouldBe "934.00"
        6789.formatDecimals() shouldBe "6,789.00"
    }

    "currency" {
        1.formatCurrency() shouldBe "1.00"
        23.formatCurrency() shouldBe "23.00"
        934.formatCurrency() shouldBe "934.00"
        6789.formatCurrency() shouldBe "6,789.00"
    }
})
