package io.slink.tsv

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

fun InputStream.readTsv(
    commentChar: Char = '#',
    charset: Charset = Charsets.UTF_8,
    processRow: (List<String>) -> Unit,
) {
    BufferedReader(InputStreamReader(this, charset)).useLines {
        it.forEach { line ->
            line.toTsvRow(commentChar)?.let(processRow)
        }
    }
}

fun InputStream.readTsv(commentChar: Char = '#', charset: Charset = Charsets.UTF_8): List<List<String>> {
    val rows = mutableListOf<List<String>>()
    readTsv(commentChar, charset) { rows.add(it) }
    return rows
}

fun String.readTsv(commentChar: Char = '#', processRow: (List<String>) -> Unit) {
    split("\n").forEach { line ->
        line.toTsvRow(commentChar)?.let(processRow)
    }
}

fun String.readTsv(commentChar: Char = '#'): List<List<String>> {
    val rows = mutableListOf<List<String>>()
    readTsv(commentChar) { rows.add(it) }
    return rows
}

fun InputStream.readTsvWithHeader(
    commentChar: Char = '#',
    charset: Charset = Charsets.UTF_8,
    processRow: (Map<String, String>) -> Unit,
) {
    var headers: List<String>? = null
    BufferedReader(InputStreamReader(this, charset)).useLines {
        it.forEach { line ->
            if (headers == null) {
                headers = line.toTsvRow(commentChar)
            } else {
                line.toTsvRow(headers!!, commentChar)?.let(processRow)
            }
        }
    }
}

fun InputStream.readTsvWithHeader(commentChar: Char = '#', charset: Charset = Charsets.UTF_8): List<Map<String, String>> {
    val rows = mutableListOf<Map<String, String>>()
    readTsvWithHeader(commentChar, charset) { rows.add(it) }
    return rows
}

fun String.readTsvWithHeader(
    commentChar: Char = '#',
    processRow: (Map<String, String>) -> Unit
) {
    var headers: List<String>? = null
    split("\n").forEach { line ->
        if (headers == null) {
            headers = line.toTsvRow(commentChar)
        } else {
            line.toTsvRow(headers!!, commentChar)?.let(processRow)
        }
    }
}

fun String.readTsvWithHeader(commentChar: Char = '#'): List<Map<String, String>> {
    val rows = mutableListOf<Map<String, String>>()
    readTsvWithHeader(commentChar) { rows.add(it) }
    return rows
}

private fun String.toTsvRow(commentChar: Char): List<String>? {
    val row = this.trim()
    return if (row.isNotEmpty() && row[0] != commentChar) {
        row.split("\t")
    } else {
        null
    }
}

private fun String.toTsvRow(headers: List<String>, commentChar: Char): Map<String, String>? {
    return toTsvRow(commentChar)
        ?.mapIndexed { index, element ->
            headers[index] to element
        }
        ?.toMap()
}