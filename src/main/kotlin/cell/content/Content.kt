package cell.content

import cell.content.value.Value

interface Content {

    fun getValue(): Value
    fun computeValue()
    fun getContentString():String

}

fun String.isNumeric(): Boolean = this.all { char -> char.isDigit() }
fun String.isFormula(): Boolean = this.startsWith("=")




