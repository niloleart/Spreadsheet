package spreadsheet.utils.saver

import cell.Cell
import cell.Coordinate
import cell.content.Content
import cell.content.FormulaContent
import spreadsheet.Spreadsheet
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.util.*


class CSVSaver : Saver {

    override fun save(path: String, spreadsheet: Spreadsheet) {
        val file = File(path)
        try {
            file.createNewFile()
        } catch (ex: IOException) {
            ex.printStackTrace(System.err)
        }

        try {
            val writer = PrintWriter(path, "UTF-8")

            for (i in 1..spreadsheet.maxRow) {
                var coordinates: Coordinate = Coordinate(i, 1)
                var optionalCell: Optional<Cell> = spreadsheet.getCell(coordinates)
                if (optionalCell.isPresent) {
                    val content: Content = optionalCell.get().content
                    if (content is FormulaContent) {
                        writer.print("=" + content.getContentString())
                    } else {
                        writer.print(content.getContentString())
                    }
                }
                for (j in 2 until spreadsheet.maxCol + 1) {
                    writer.print(",")
                    coordinates = Coordinate(i, j)
                    optionalCell = spreadsheet.getCell(coordinates)
                    if (optionalCell.isPresent) {
                        val content: Content = optionalCell.get().content
                        if (content is FormulaContent) {
                            writer.print("=" + content.getContentString())
                        } else {
                            writer.print(content.getContentString())
                        }
                    }
                }
                writer.println("")
            }
            writer.close()
        } catch (e: Exception) {
            println("Error!!!")
        }
    }
}
