package cell

import cell.content.formula.Operand
import cell.content.function.Argument
import java.util.Objects

class Coordinate() : Operand(), Argument {
    var row: Int = 0
    var column: Int = 0
    private lateinit var coordinates: String

    constructor(row: Int, column: Int) : this() {
        this.row = row
        this.column = column
        this.coordinates = toAlphabetic(column-1) + row
    }

    constructor(coordinates: String): this() {
        this.coordinates = coordinates
        val parsedCoordinates = coordinates.split("(?<=\\D)(?=\\d)".toRegex()).toTypedArray()
        row = parsedCoordinates[1].toInt()
        val column = parsedCoordinates[0]
        this.column = 0
        for ((i, letter: Char) in column.uppercase().toCharArray().withIndex()) {
            this.column += (letter - 'A') + 25 * i + 1
        }
    }

    override fun toString(): String {
        return coordinates
    }

    override fun hashCode() = Objects.hash(row, column)

    override fun equals(other: Any?) =  if (other !is Coordinate) {
        false
    } else {
        other.row == row && other.column == column
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