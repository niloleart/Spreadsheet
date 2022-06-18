package cell

import cell.content.formula.Operand
import cell.content.formula.computation.Visitor
import cell.content.function.Argument

class Coordinate(

) : Operand(), Argument {
    var row: Int = 0
    var column: Int = 0
    private lateinit var coordinates: String

    constructor(coordinates: String): this() {
        this.coordinates = coordinates
        val parsedCoordinates = coordinates.split("(?<=\\D)(?=\\d)".toRegex()).toTypedArray()
        row = parsedCoordinates[1].toInt()
        val column = parsedCoordinates[0]
        this.column = 0
        var i =0
        for (letter: Char in column.uppercase().toCharArray()) {
            this.column += (letter - 'A') + 25 * i + 1
            i++
        }
    }

    constructor(row: Int, column: Int) : this() {
        this.row = row
        this.column = column
        this.coordinates = toAlphabetic(column-1) + row
    }

    fun toAlphabetic(i: Int): String {
        val quot = i / 26
        val rem = i % 26
        val letter = ('A'.code + rem).toChar()
        return if (quot == 0) {
            "" + letter
        } else {
            toAlphabetic(quot - 1) + letter
        }
    }
}