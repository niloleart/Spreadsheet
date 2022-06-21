package spreadsheet.utils.loader

import spreadsheet.Spreadsheet

interface Loader {
    fun load(path: String): Spreadsheet
}