package cell.content.function

import cell.content.value.NumberValue
import spreadsheet.Spreadsheet

class FunctionMin : Function() {

    override fun compute(spreadsheet: Spreadsheet): NumberValue {
        var minValue: NumberValue? = null

        getArguments()?.let { args ->
            args.forEach {
                val numberValue = getArgumentValue(spreadsheet, it)
                if (minValue == null) {
                    minValue = numberValue
                } else if (minValue!!.getAsDouble() > numberValue.getAsDouble()) {
                    minValue = numberValue
                }
            }
        }
        value = minValue
        return value!!
    }
}