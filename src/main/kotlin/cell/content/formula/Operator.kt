package cell.content.formula

import cell.content.formula.computation.Visitor
import cell.content.value.NumberValue
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.tokens.IToken

class Operator(val symbol: String) : Component {

    override fun acceptVisitor(visitor: Visitor) {
        visitor.visit(this)
    }

    fun applyOperation(e2: NumberValue, e1: NumberValue)  =
        when(symbol) {
            "+" -> NumberValue(e1.getAsDouble() + e2.getAsDouble())
            "*" -> NumberValue(e1.getAsDouble() * e2.getAsDouble())
            "-" -> NumberValue(e1.getAsDouble() - e2.getAsDouble())
            "/" -> NumberValue(e1.getAsDouble() / e2.getAsDouble())
            else -> NumberValue()
        }

}