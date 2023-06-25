package io.slink.string

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class StringsCodecTest : StringSpec({

    "encodeBase64" {
        "abc".encodeBase64() shouldBe "YWJj"
        "être établi avec son frère".encodeBase64() shouldBe "w6p0cmUgw6l0YWJsaSBhdmVjIHNvbiBmcsOocmU"
    }

    "decodeBase64" {
        "YWJj".decodeBase64() shouldBe "abc"
        "w6p0cmUgw6l0YWJsaSBhdmVjIHNvbiBmcsOocmU".decodeBase64() shouldBe "être établi avec son frère"
    }

    "symmetry" {
        fun testString(str:String){
            str.encodeBase64().decodeBase64() shouldBe str
        }

        testString(UUID.randomUUID().toString())
        testString("lorem ipsum")
        testString("Алішером Усмановим (її братом), який активно підтримував матеріально")
    }
})