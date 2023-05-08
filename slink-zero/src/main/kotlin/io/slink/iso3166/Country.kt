package io.slink.iso3166

import io.slink.resources.resourceAsString
import io.slink.string.cleanWhitespace
import io.slink.tsv.readTsvWithHeader

data class Country(
    val alpha2Code: String,
    val alpha3Code: String,
    val name: String,
    val otherNames: List<String>
) {

    companion object {

        val allCountries: List<Country> = resourceAsString("slink/iso3166/iso3166-countries.tsv").readTsvWithHeader()
            .map { row ->
                val alpha2Code = row["Alpha2Code"]!!
                val alpha3Code = row["Alpha3Code"]!!
                val names = row["Names"]!!.split(";").map { it.cleanWhitespace() }
                Country(
                    alpha2Code = alpha2Code,
                    alpha3Code = alpha3Code,
                    name = names.first(),
                    otherNames = if (names.size > 1) names.subList(1, names.size) else emptyList()
                )
            }

        private val countriesByCode: Map<String, Country> = allCountries
            .associateBy { it.alpha2Code.uppercase() }
            .plus(allCountries.associateBy { it.alpha3Code.uppercase() })

        fun byCode(alpha2Or3Code: String): Country {
            return countriesByCode[alpha2Or3Code.uppercase()]!!
        }
    }
}