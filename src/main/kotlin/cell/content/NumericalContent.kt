package cell.content

import cell.content.value.NumberValue
import cell.content.value.Value

class NumericalContent(private val contentStr: String): Content {
    lateinit var numberValue: NumberValue

    override fun getValue(): Value {
        return numberValue
    }

    override fun computeValue() {
        numberValue = NumberValue(contentStr.toDouble())
    }

    override fun getContentString(): String {
        return contentStr
    }

}