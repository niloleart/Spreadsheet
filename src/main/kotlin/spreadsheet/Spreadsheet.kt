package spreadsheet

import cell.Cell
import cell.Coordinate
import java.util.*

class Spreadsheet(
    var cells: MutableMap<Coordinate, Cell>,
    var maxRow: Int = 0,
    var maxCol: Int = 0
) : SpreadsheetInterface {

    override fun getCell(coordinate: Coordinate): Optional<Cell> {
        return Optional.ofNullable(cells[coordinate])
    }

    override fun addCell(cell: Cell) {
        cells[cell.coordinate] = cell
        val row = cell.coordinate.row
        val col = cell.coordinate.column
        maxRow = Integer.max(row, maxRow)
        maxCol = Integer.max(col, maxCol)
    }
}