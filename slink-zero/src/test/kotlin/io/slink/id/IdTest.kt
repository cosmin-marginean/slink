package io.slink.id

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldMatch

class IdTest : StringSpec({

    "uuid" {
        uuid() shouldMatch "((\\d)|([a-z])){32}".toRegex()
    }
})
