package cell.content.function

import cell.content.formula.Operand

abstract class Function(
    private var arguments: List<Argument>
) : Operand {
}