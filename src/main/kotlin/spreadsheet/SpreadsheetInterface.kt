package spreadsheet

import cell.Cell
import cell.Coordinate
import java.util.*

interface SpreadsheetInterface {
    fun getCell(coordinate: Coordinate): Optional<Cell>
    fun addCell(cell: Cell)
}