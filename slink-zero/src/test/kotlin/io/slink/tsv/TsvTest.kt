package io.slink.tsv

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.slink.resources.resourceAsInput
import io.slink.resources.resourceAsString

class TsvTest : StringSpec({

    val testDataNoHeader = listOf(
        listOf("John Smith", "33", "john@smith.com"),
        listOf("Jane Doe", "35", "jane@doe.com")
    )

    val testDataWithHeader = listOf(
        mapOf("Name" to "John Smith", "Age" to "33", "Email" to "john@smith.com"),
        mapOf("Name" to "Jane Doe", "Age" to "35", "Email" to "jane@doe.com")
    )

    "simple tsv" {
        resourceAsInput("tsv/tsv-default.tsv").readTsv() shouldBe testDataNoHeader
        resourceAsString("tsv/tsv-default.tsv").readTsv() shouldBe testDataNoHeader

        val elements = mutableListOf<List<String>>()
        resourceAsInput("tsv/tsv-default.tsv").readTsv { elements.add(it) }
        elements shouldBe testDataNoHeader

        val elementsStr = mutableListOf<List<String>>()
        resourceAsString("tsv/tsv-default.tsv").readTsv { elementsStr.add(it) }
        elementsStr shouldBe testDataNoHeader
    }

    "tsv with empty lines and comments" {
        resourceAsInput("tsv/tsv-empty-lines-and-comments.tsv").readTsv() shouldBe testDataNoHeader
        resourceAsString("tsv/tsv-empty-lines-and-comments.tsv").readTsv() shouldBe testDataNoHeader
    }

    "tsv with empty lines and custom comment char" {
        resourceAsInput("tsv/tsv-custom-comment-char.tsv").readTsv('!') shouldBe testDataNoHeader
        resourceAsString("tsv/tsv-custom-comment-char.tsv").readTsv('!') shouldBe testDataNoHeader
    }

    "tsv with headers" {
        resourceAsInput("tsv/tsv-with-headers.tsv").readTsvWithHeader() shouldBe testDataWithHeader
        resourceAsString("tsv/tsv-with-headers.tsv").readTsvWithHeader() shouldBe testDataWithHeader

        val elements = mutableListOf<Map<String, String>>()
        resourceAsInput("tsv/tsv-with-headers.tsv").readTsvWithHeader { elements.add(it) }
        elements shouldBe testDataWithHeader

        val elementsStr = mutableListOf<Map<String, String>>()
        resourceAsString("tsv/tsv-with-headers.tsv").readTsvWithHeader { elementsStr.add(it) }
        elementsStr shouldBe testDataWithHeader
    }

    "tsv with headers, comments and empty lines" {
        resourceAsInput("tsv/tsv-headers-comments-empty-lines.tsv").readTsvWithHeader() shouldBe testDataWithHeader
        resourceAsString("tsv/tsv-headers-comments-empty-lines.tsv").readTsvWithHeader() shouldBe testDataWithHeader
    }

    "tsv with headers, custom comments and empty lines" {
        resourceAsInput("tsv/tsv-headers-custom-comments-empty-lines.tsv").readTsvWithHeader('%') shouldBe testDataWithHeader
        resourceAsString("tsv/tsv-headers-custom-comments-empty-lines.tsv").readTsvWithHeader('%') shouldBe testDataWithHeader

        val elements = mutableListOf<Map<String, String>>()
        resourceAsInput("tsv/tsv-headers-custom-comments-empty-lines.tsv").readTsvWithHeader('%') { elements.add(it) }
        elements shouldBe testDataWithHeader

        val elementsStr = mutableListOf<Map<String, String>>()
        resourceAsString("tsv/tsv-headers-custom-comments-empty-lines.tsv").readTsvWithHeader('%') { elementsStr.add(it) }
        elementsStr shouldBe testDataWithHeader
    }
})