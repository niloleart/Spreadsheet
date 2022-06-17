package cell.content

import cell.content.formula.FormulaComponent
import cell.content.formula.Operand
import cell.content.formula.Operator
import cell.content.value.NumberValue

class FormulaContent(
    var totalValue: NumberValue
) : Content(totalValue) {
    lateinit var operators: List<Operator>
    lateinit var operands: List<Operand>

}