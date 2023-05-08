package io.slink.files

import java.io.File

fun workingDirectory(): File {
    return File(System.getProperty("user.dir"))
}
