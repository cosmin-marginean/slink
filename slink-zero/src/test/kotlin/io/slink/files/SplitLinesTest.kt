package io.slink.files

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.slink.resources.resourceAsInput
import io.slink.resources.resourceAsString
import java.io.File

class SplitLinesTest : StringSpec() {

    init {
        "split file lines" {
            testSplitFile(50, 2)
            testSplitFile(2, 50)
            testSplitFile(100, 1)
            testSplitFile(1, 100)
            testSplitFile(25, 4)
            testSplitFile(23, 5)
            testSplitFile(30, 4)
        }

        "split inputstream line" {
            testSplitInputStream(50, 2)
            testSplitInputStream(2, 50)
            testSplitInputStream(100, 1)
            testSplitInputStream(1, 100)
            testSplitInputStream(25, 4)
            testSplitInputStream(23, 5)
            testSplitInputStream(30, 4)
        }
    }

    private fun testSplitFile(linesPerFile: Int, expectFiles: Int) {
        withTempDir { tempDir ->
            val file = tempDir.newFile("jsonl")
            val expectedText = resourceAsString("fileutils/split-new-line.txt")
            file.writeText(expectedText)
            val parentDir = tempDir.newDirectory()
            val files = file.splitLines(parentDir, linesPerFile)
            checkFiles(files, expectFiles, parentDir, file.nameWithoutExtension, file.extension, linesPerFile, expectedText)
        }
    }

    private fun testSplitInputStream(linesPerFile: Int, expectFiles: Int) {
        withTempDir { tempDir ->
            val parentDir = tempDir.newDirectory()
            val files = resourceAsInput("fileutils/split-new-line.txt").splitLines(parentDir, linesPerFile, "myfile", "txt")
            val expectedText = resourceAsString("fileutils/split-new-line.txt")
            checkFiles(files, expectFiles, parentDir, "myfile", "txt", linesPerFile, expectedText)
        }
    }

    private fun checkFiles(
        files: List<File>,
        expectFiles: Int,
        parentDir: File,
        baseName: String,
        extension: String,
        linesPerFile: Int,
        text: String,
    ): String {
        files.size shouldBe expectFiles
        parentDir.list().toList().sorted() shouldBe ((1..files.size).map { "${baseName}-${it}.${extension}" }).toList()
            .sorted()

        files.forEachIndexed { index, splitFile ->
            if (index < files.size - 1) {
                splitFile.readLines().size shouldBe linesPerFile
            } else {
                splitFile.readLines().size shouldBe 100 - (files.size - 1) * linesPerFile
            }
        }
        val joinedText = files.map { it.readText() }.joinToString("")
        return joinedText shouldBe text
    }
}
