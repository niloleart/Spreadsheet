package cell.content.formula.computation

import cell.Coordinate
import cell.content.formula.Component
import cell.content.formula.Operand
import cell.content.formula.Operator
import cell.content.formula.evaluators.ExpressionEvaluator
import cell.content.value.NumberValue
import spreadsheet.Spreadsheet
import java.util.*

class PostfixEvaluator(val spreadsheet: Spreadsheet, var stack: Stack<NumberValue> = Stack<NumberValue>()): Visitor, ExpressionEvaluator {


    override fun evaluateExpression(components: List<Component>): NumberValue {
        for (component in components) {
            component.acceptVisitor(this)
        }
        return this.stack.pop()
    }

    override fun visit(operand: Operand) {
       this.stack.push(getOperandValue(operand))
    }

    override fun visit(operator: Operator) {
        val result = operator.applyOperation(this.stack.pop(), this.stack.pop())
        this.stack.push(result)
    }

    private fun getOperandValue(operand: Operand): NumberValue {
        var numberValue = NumberValue()
        if (operand is cell.content.function.Function) {
            numberValue = operand.compute(spreadsheet)
        } else if (operand is Coordinate) {
            val optCell = spreadsheet.getCell(operand)
            if (optCell.isPresent) {
                val cell = optCell.get()
                try {
                    numberValue = cell.content.getValue() as NumberValue
                } catch (e: java.lang.ClassCastException) {
                    throw Exception("Tried to cast non numerical value to numerical value")
                }
            }
        } else {
            numberValue = operand as NumberValue
        }
        return numberValue
    }


}