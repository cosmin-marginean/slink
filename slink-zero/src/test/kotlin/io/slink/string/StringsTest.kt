package io.slink.string

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.*

class StringsTest : StringSpec({

    "regex tests" {
        "a^$.b=1".replace(REGEX_NON_ALPHA, "") shouldBe "ab1"
        "   a \t \n b \t1 ".replace(REGEX_WHITESPACE, "") shouldBe "ab1"
    }

    "cleanWhitespace" {
        "   a \t \n b \t1 ".cleanWhitespace() shouldBe "a b 1"
        "\u200B my \u200B text \u200B \u200B".cleanWhitespace() shouldBe "my text"
    }

    "removeWhitespace" {
        "   a \t \n b \t1 ".removeWhitespace() shouldBe "ab1"
    }

    "domain" {
        "test.com".domain() shouldBe "test.com"
        "abc.test.com".domain() shouldBe "abc.test.com"
        "http://abc.test.com".domain() shouldBe "abc.test.com"
        "https://abc.test.com".domain() shouldBe "abc.test.com"
        "https://ABC.test.com/something".domain() shouldBe "abc.test.com"
        "https://abc.test.com:8080/something".domain() shouldBe "abc.test.com"
        "localHOST:8080".domain() shouldBe "localhost"
        "localhost:8080/something".domain() shouldBe "localhost"
    }

    "clean email" {
        "John.SMITH@GMail.com".cleanEmail() shouldBe "john.smith@gmail.com"
        " John.SMITH@GMail.com  \t".cleanEmail() shouldBe "john.smith@gmail.com"
    }

    "ellipsis" {
        "stop".ellipsis(5) shouldBe "stop"
        "stop".ellipsis(4) shouldBe "stop"
        "spice world".ellipsis(6) shouldBe "spi..."
    }

    "anonymize string" {
        "test".anonymize() shouldBe "****"
        "1234567893432345".anonymize() shouldBe "****************"
        "1234567893430146".anonymize(4) shouldBe "************0146"
        "test".anonymize(4) shouldBe "test"
        "test".anonymize(5) shouldBe "test"
        "test".anonymize(1) shouldBe "***t"
    }

    "anonymize email" {
        "john.smith@test.com".anonymizeEmail() shouldBe "**********@********"
        "john.smith@test.com".anonymizeEmail(4) shouldBe "******mith@****.com"
    }

    "toLines" {
        " aa \n bbb\n\n\n c \n\n".toLines() shouldBe listOf("aa", "bbb", "c")
    }

    "urlEncode" {
        "computers & internet".urlEncode() shouldBe "computers+%26+internet"
    }

    "titleCaseFirstChar" {
        "something else".titleCaseFirstChar() shouldBe "Something else"
        "SOMETHING ELSE".titleCaseFirstChar() shouldBe "SOMETHING ELSE"
        "Ǆemal".titleCaseFirstChar(Locale("hr", "HR")) shouldBe "ǅemal"
        "ǆemal".titleCaseFirstChar() shouldBe "ǅemal"
        "".titleCaseFirstChar() shouldBe ""
    }

    "nullIfBlank" {
        "".nullIfBlank() shouldBe null
        "  ".nullIfBlank() shouldBe null
        "    \t \n".nullIfBlank() shouldBe null
        null.nullIfBlank() shouldBe null
        "aa".nullIfBlank() shouldBe "aa"
    }

    "kebab to camel case" {
        "company-information".kebabToCamelCase() shouldBe "companyInformation"
        "company".kebabToCamelCase() shouldBe "company"
    }

    "camel to kebab case" {
        "companyInformation".camelToKebabCase() shouldBe "company-information"
        "company".camelToKebabCase() shouldBe "company"
        "company-information".camelToKebabCase() shouldBe "company-information"
    }

    "enum label" {
        TestEnum.THIS.enumLabel() shouldBe "This"
        TestEnum.SOMETHING_ELSE.enumLabel() shouldBe "Something else"
    }

    "enum label string" {
        "PENDING".enumLabel() shouldBe "Pending"
        "NOT_STARTED".enumLabel() shouldBe "Not started"
    }
})

enum class TestEnum {
    THIS,
    SOMETHING_ELSE
}
