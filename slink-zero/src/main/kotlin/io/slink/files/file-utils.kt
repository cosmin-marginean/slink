package io.slink.files

import java.io.BufferedWriter
import java.io.File
import java.io.InputStream

fun workingDirectory(): File {
    return File(System.getProperty("user.dir"))
}

fun File.splitLines(outputDirectory: File, linesPerFile: Int): List<File> {
    return inputStream().use {
        it.splitLines(outputDirectory, linesPerFile, this.nameWithoutExtension, this.extension)
    }
}

fun InputStream.splitLines(outputDirectory: File, linesPerFile: Int, baseName: String, extension: String = ""): List<File> {
    val files = mutableListOf<File>()
    var crtFileWriter: BufferedWriter? = null
    var fileIndex = 0

    bufferedReader()
        .useLines { lines ->
            lines.forEachIndexed { index, line ->
                if (crtFileWriter == null) {
                    val nextFile = File(outputDirectory, baseName + "-${fileIndex + 1}." + extension)
                    files.add(nextFile)
                    crtFileWriter = nextFile.bufferedWriter()
                }
                crtFileWriter!!.write(line)
                crtFileWriter!!.write("\n")
                if (index % linesPerFile == linesPerFile - 1) {
                    crtFileWriter!!.close()
                    crtFileWriter = null
                    fileIndex++
                }
            }
        }
    crtFileWriter?.close()

    return files
}