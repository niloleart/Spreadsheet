package cell.content.function

import cell.Cell
import cell.CellRange
import cell.Coordinate
import cell.content.formula.Operand
import cell.content.value.NumberValue
import cell.content.value.Value
import spreadsheet.Spreadsheet
import java.util.*

abstract class Function : Operand(), Argument {

    var value: NumberValue? = null
    private var arguments: MutableList<Argument> = ArrayList()

    open fun getValue(): Value? {
        return value
    }

    open fun addArgument(argument: Argument) {
        if (argument is CellRange) {
           val coordinates: List<Coordinate> = argument.getRangeCoordinates()
            arguments.addAll(coordinates)
        } else {
            arguments.add(argument)
        }
    }

    open fun getArguments(): List<Argument>? {
        return arguments
    }

    abstract fun compute(spreadsheet: Spreadsheet): NumberValue

    protected open fun getArgumentValue(spreadsheet: Spreadsheet, argument: Argument): NumberValue {
        var numValue = NumberValue()
        if (argument is Function) {
            numValue = argument.compute(spreadsheet)
        } else if (argument is Coordinate) {
            val optCell: Optional<Cell> = spreadsheet.getCell(argument as Coordinate)
            if (optCell.isPresent) {
                val cell: Cell = optCell.get()
                numValue = try {
                    cell.content.getValue() as NumberValue
                } catch (e: ClassCastException) {
                    throw java.lang.Exception("Tried to cast non NumValue to NumValue")
                }
            }
        } else {
            numValue = argument as NumberValue
        }
        return numValue
    }

    override fun toString(): String {
        return this.javaClass.simpleName + arguments
    }
}