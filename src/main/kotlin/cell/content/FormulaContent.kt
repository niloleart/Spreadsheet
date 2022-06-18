package cell.content

import cell.content.formula.Component
import cell.content.formula.FormulaProcessor
import cell.content.value.NumberValue
import cell.content.value.StringValue
import cell.content.value.Value

class FormulaContent(
    private var stringValue: String
    ) : Content {
    private lateinit var numberValue: NumberValue
    lateinit var formulaProcessor: FormulaProcessor
    private lateinit var formulaExpression: List<Component>

    override fun getValue(): Value {
        return numberValue
    }

    override fun computeValue() {
        numberValue = formulaProcessor.computeFormulaValue(this)
        formulaExpression = formulaProcessor.expression
    }

    //TODO
    fun computeValue(recompute: Boolean) {
        if (recompute) {
            //stringValue = formulaProcessor.recomputeFormulaValue(this)
            formulaExpression = formulaProcessor.expression
        } else {
            computeValue()
        }
    }

    override fun getContentString(): String {
        return stringValue
    }




}
