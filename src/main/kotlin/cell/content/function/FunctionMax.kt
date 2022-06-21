package cell.content.function

import cell.content.value.NumberValue
import spreadsheet.Spreadsheet

class FunctionMax: Function() {

    override fun compute(spreadsheet: Spreadsheet): NumberValue {
        var maxValue: NumberValue? = null

        getArguments()?.let {
            for (arg in it) {
                val numberValue = getArgumentValue(spreadsheet,arg)
                if (maxValue == null) {
                    maxValue = numberValue
                } else if (maxValue!!.getAsDouble() < numberValue.getAsDouble()) {
                    maxValue = numberValue
                }
            }
        }
        value = maxValue
        return value!!
    }
}