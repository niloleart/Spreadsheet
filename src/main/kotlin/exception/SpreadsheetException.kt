package exception

import cell.Coordinate
import main
import userinterface.utils.*

open class SpreadsheetException(private val errorMessage: String): Exception(errorMessage) {

    fun printError() {
        println(COLOR_RED + errorMessage + COLOR_ORIGINAL)
        main()
    }
}

class SpreadsheetNotInitializedException(errorMessage: String = E_NOT_INITIALIZED): SpreadsheetException(errorMessage)
class BadEditCellInputException(errorMessage: String = E_BAD_CELL_INPUT): SpreadsheetException(errorMessage)
class InvalidCellContentException(errorMessage: String = E_BAD_CELL_CONTENT): SpreadsheetException(errorMessage)
class CircularDependencyException(parentCell: Coordinate, childCell: Coordinate) : SpreadsheetException(E_CIRCULAR_DEPENDENCY + "CELL " + parentCell.toString().uppercase() + " depends on " + childCell.toString().uppercase())
class BadCellContentException(errorMessage: String = "Text cell in a formula not allowed"): SpreadsheetException(errorMessage)