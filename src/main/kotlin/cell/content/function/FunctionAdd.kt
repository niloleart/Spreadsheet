package cell.content.function

import cell.content.value.NumberValue
import spreadsheet.Spreadsheet

class FunctionAdd : Function() {
    override fun compute(spreadsheet: Spreadsheet?): NumberValue {
        var sum = 0.0
        val arguments = getArguments()
        if (arguments != null) {
            for (arg in arguments) {
                val numValue: NumberValue? = getArgumentValue(spreadsheet!!, arg)
                sum += numValue?.getAsDouble() ?: 0.0
            }
        }

        value = NumberValue(sum)
        return this.value!!
    }

}