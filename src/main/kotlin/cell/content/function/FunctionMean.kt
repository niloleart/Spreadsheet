package cell.content.function

import cell.content.value.NumberValue
import spreadsheet.Spreadsheet

class FunctionMean(): Function() {
    override fun compute(spreadsheet: Spreadsheet?): NumberValue {
        return NumberValue()
    }
}