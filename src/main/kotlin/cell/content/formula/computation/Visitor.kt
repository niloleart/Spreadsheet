package cell.content.formula.computation

import cell.content.formula.Operand
import cell.content.formula.Operator

interface Visitor {
    fun visit(operand: Operand)
    fun visit(operator: Operator)
}