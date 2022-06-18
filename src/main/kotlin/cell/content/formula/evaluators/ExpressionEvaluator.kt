package cell.content.formula.evaluators

import cell.content.formula.Component
import cell.content.formula.Operand
import cell.content.formula.Operator
import cell.content.value.NumberValue
import spreadsheet.Spreadsheet
import java.util.*

interface ExpressionEvaluator {
    fun evaluateExpression(components: List<Component>): NumberValue
}