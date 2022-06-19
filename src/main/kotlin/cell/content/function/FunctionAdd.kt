package cell.content.function

import cell.content.value.NumberValue
import spreadsheet.Spreadsheet

class FunctionAdd : Function() {

    override fun compute(spreadsheet: Spreadsheet): NumberValue {
        var sum = 0.0
        getArguments()?.let { args ->
            args.forEach { arg ->
                val numValue = getArgumentValue(spreadsheet, arg)
                sum += numValue.getAsDouble()
            }
        }

        value = NumberValue(sum)
        return value!!
    }

}