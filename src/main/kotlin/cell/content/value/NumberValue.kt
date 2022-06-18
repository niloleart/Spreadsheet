package cell.content.value

import cell.content.formula.Operand
import cell.content.function.Argument

class NumberValue(private val value: Double = Double.NaN) : Value, Argument, Operand() {


    override fun getAsDouble(): Double {
        return value
    }

    override fun getAsString(): String {
        return value.toString()
    }

}