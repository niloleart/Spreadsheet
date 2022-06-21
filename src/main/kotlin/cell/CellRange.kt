package cell

import cell.content.function.Argument

class CellRange(
    cellRange: String
) : Argument{
    private var startCell: Coordinate
    private var endCell: Coordinate

    init {
        val rangeList = cellRange.split(":")
        startCell = Coordinate(rangeList[0])
        endCell = Coordinate(rangeList[1])
    }

    fun getRangeCoordinates(): List<Coordinate> {
        val coordinates: MutableList<Coordinate> = ArrayList<Coordinate>()
        var rowIterator: Int = startCell.row
        val rowEnd: Int = endCell.row
        val columnEnd: Int = endCell.column

        while (rowIterator <= rowEnd) {
            var columnIterator: Int = startCell.column
            while (columnIterator <= columnEnd) {
                coordinates.add(Coordinate(rowIterator, columnIterator))
                columnIterator++
            }
            rowIterator++
        }
        return coordinates
    }

    override fun toString(): String {
        return "$startCell:$endCell"
    }
}