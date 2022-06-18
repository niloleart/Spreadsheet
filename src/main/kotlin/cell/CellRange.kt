package cell

import cell.content.function.Argument

class CellRange(
    private var originCell: Cell,
    private var finalCell: Cell
) : Argument{
}