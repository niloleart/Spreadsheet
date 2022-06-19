package cell.content.function

import cell.content.value.NumberValue
import spreadsheet.Spreadsheet

class FunctionMean: Function() {

    override fun compute(spreadsheet: Spreadsheet): NumberValue {
        var mean = 0.0
        var count = 0

        getArguments()?.let { args ->
            args.forEach {
                val numberValue = getArgumentValue(spreadsheet, it)
                mean  += numberValue.getAsDouble()
                count++
            }

        }
        mean /= count
        value = NumberValue(mean)
        return value!!
    }
}