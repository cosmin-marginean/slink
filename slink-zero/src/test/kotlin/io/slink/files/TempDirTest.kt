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
})