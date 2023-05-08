package io.slink.iso3166

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CountriesTest : StringSpec({

    val uk = Country(
        "GB", "GBR", "United Kingdom", listOf(
            "UK", "Britain", "Great Britain", "United Kingdom of Great Britain and Northern Ireland"
        )
    )

    "country by code" {
        Country.byCode("gb") shouldBe uk
        Country.byCode("GBr") shouldBe uk
    }
})