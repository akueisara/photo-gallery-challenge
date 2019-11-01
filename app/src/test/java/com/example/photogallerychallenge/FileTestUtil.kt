package com.example.photogallerychallenge

import androidx.annotation.VisibleForTesting
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

@Throws(IOException::class)
fun readContentFromFile(filePath: String): String {
    val br = BufferedReader(InputStreamReader(FileInputStream(filePath)))
    val sb = StringBuilder()
    var line = br.readLine()
    while (line != null) {
        sb.append(line)
        line = br.readLine()
    }

    return sb.toString()
}