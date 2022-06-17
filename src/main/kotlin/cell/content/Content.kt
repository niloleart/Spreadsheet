package cell.content

import cell.content.formula.FormulaComponent
import cell.content.value.NumberValue
import cell.content.value.StringValue
import cell.content.value.Value

abstract class Content(val value: Value) {

    fun computeValue() {

    }

}

fun String.isNumeric(): Boolean = this.all { char -> char.isDigit() }
fun String.canBeFormula(): Boolean = this.chars().findFirst().equals("=")

fun String.setContent(): Content {
         return if (this.isNumeric()) {
            NumericalContent(NumberValue(this.toDouble()))
       } else if (this.canBeFormula()) {
           //TODO
            FormulaContent(NumberValue(0.0))
        } else {
            TextContent(StringValue(this))
        }
}


