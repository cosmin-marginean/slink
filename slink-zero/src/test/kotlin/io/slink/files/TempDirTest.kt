package io.slink.files

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.slink.id.uuid
import java.io.File

class TempDirTest : StringSpec({

    "should not exist on finish" {
        var dir: File? = null
        withTempDir { tempdir ->
            dir = tempdir.directory
            tempdir.newDirectory().exists() shouldBe true
        }
        dir!!.exists() shouldBe false
    }

    "file content" {
        withTempDir { tempdir ->
            val newFile = tempdir.newFile()
            val content = "test " + uuid()
            newFile.writeText(content)
            newFile.readText() shouldBe content
        }
    }

    "custom directory" {
        withTempDir(File("temp")) { tempdir ->
            val newFile = tempdir.newFile()
            val content = "test " + uuid()
            newFile.writeText(content)
            newFile.readText() shouldBe content
        }
    }

    "custom extension" {
        withTempDir { tempdir ->
            val newFile1 = tempdir.newFile("txt")
            val newFile2 = tempdir.newFile(".txt")

            val content1 = "test " + uuid()
            newFile1.writeText(content1)

            val content2 = "test " + uuid()
            newFile2.writeText(content2)

            tempdir.directory.list().size shouldBe 2
            tempdir.directory.listFiles().forEach { file ->
                println(file.name)
                file.name.matches("[a-zA-Z\\d]+\\.txt".toRegex()) shouldBe true
            }
            newFile1.readText() shouldBe content1
            newFile2.readText() shouldBe content2
        }

    }
})