package spreadsheet.utils.saver

import spreadsheet.Spreadsheet

interface Saver {
    fun save(path: String, spreadsheet: Spreadsheet)
}