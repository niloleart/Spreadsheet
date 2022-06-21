package spreadsheet.utils.loader

import cell.Cell
import cell.Coordinate
import cell.content.Content
import cell.content.FormulaContent
import cell.content.formula.FormulaProcessor
import spreadsheet.Spreadsheet
import spreadsheet.SpreadsheetController
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.system.exitProcess

open class CSVLoader(private val spreadsheetController: SpreadsheetController, private val formulaProcessor: FormulaProcessor): Loader {

    override fun load(path: String): Spreadsheet {
        val file = File(path)
        val scanner: Scanner

        try {
            scanner = Scanner(file)
        } catch (e: FileNotFoundException) {
            println(e.message)
            exitProcess(666)
        }
        spreadsheetController.create()

        var row = 1

        while (scanner.hasNextLine()) {
            val line = scanner.nextLine()
            val contents = line.split(",")

            for (i in contents.indices) {
                if (contents[i].isNotEmpty()) {
                    val coordinate = Coordinate(row, i+1)
                    setCellContent(coordinate.toString(), contents[i])
                }
            }
            println(line)
            row++
        }
        return spreadsheetController.spreadsheet
    }

    private fun setCellContent(coordinate: String, content: String) {
        spreadsheetController.setCellContent(coordinate, content)
    }


}