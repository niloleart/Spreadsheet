package cell

import cell.content.Content
import cell.content.formula.Operand

class Cell(
    private var coordinate: Coordinate,
    private var content: Content
) : Operand {
}