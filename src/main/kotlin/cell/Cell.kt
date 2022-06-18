package cell

import cell.content.Content
import cell.content.formula.Operand
import cell.content.function.Argument
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.formula.tokens.Token

class Cell(
    var coordinate: Coordinate
) : Argument {
    lateinit var content: Content
}