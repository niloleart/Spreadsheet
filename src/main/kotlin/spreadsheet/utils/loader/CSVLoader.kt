package spreadsheet.utils.loader

import java.io.File
import java.io.IOException

open class CSVLoader: Loader {

    @Throws(IOException::class)
    fun File.readAsCSV(): List<List<String>> {
        val splitLines = mutableListOf<List<String>>()
        forEachLine {
            splitLines += it.split(", ")
        }
        return splitLines
    }
}