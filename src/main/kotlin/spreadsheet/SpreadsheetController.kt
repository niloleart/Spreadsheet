package spreadsheet

import cell.Cell
import cell.Coordinate
import cell.content.*
import cell.content.formula.Component
import cell.content.formula.FormulaProcessor
import cell.content.formula.computation.PostfixEvaluator
import cell.content.function.*
import cell.content.function.Function
import cell.content.value.NumberValue
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.expression.FormulaException
import exception.*
import spreadsheet.utils.loader.CSVLoader
import spreadsheet.utils.loader.Loader
import spreadsheet.utils.saver.CSVSaver
import spreadsheet.utils.saver.Saver
import userinterface.utils.E_BAD_CELL_COORDINATES
import java.beans.PropertyChangeListener
import java.util.*

class SpreadsheetController {

    lateinit var spreadsheet: Spreadsheet
    private lateinit var csvSaver: Saver
    lateinit var csvLoader: Loader

    fun isSpreadsheetInitialized(): Boolean {
        if (this::spreadsheet.isInitialized) {
            return true
        } else {
            throw SpreadsheetNotInitializedException()
        }
    }

    fun create() {
        spreadsheet = Spreadsheet(mutableMapOf(), 0, 0)
    }

    fun saveSpreadsheet(path: String) {
        try {
            isSpreadsheetInitialized()
        } catch (e: SpreadsheetNotInitializedException) {
            throw e
        }
        csvSaver = CSVSaver()
        csvSaver.save(path, spreadsheet)
    }

    fun loadSpreadsheet(path: String) {
        csvLoader = CSVLoader()
        //TODO
        //spreadsheet = csvLoader.load(path)
    }

    fun editCell(userInput: List<String>) {
        try {
            isSpreadsheetInitialized()
        } catch (e: SpreadsheetNotInitializedException) {
            throw e
        }

        try {
            isEditCellInputCorrect(userInput)
        } catch (e: BadEditCellInputException) {
            throw e
        }

        try {
            isCoordinate(userInput[1])
        } catch (e: BadEditCellInputException) {
            throw e
        }

        try {
            val input = userInput.subList(2, userInput.size).joinToString(separator = " ")
            setCellContent(userInput[1], input)
        } catch (e: SpreadsheetException) {
            throw e
        }
    }

    private fun isEditCellInputCorrect(userInput: List<String>) {
       if (userInput.size < 3) {
           throw BadEditCellInputException()
       }
    }

    private fun isCoordinate(cellCoordinate: String) {
        if (!cellCoordinate.first().isLetter() || !cellCoordinate.last().isDigit()) {
            throw BadEditCellInputException(E_BAD_CELL_COORDINATES)
        }
    }

    private fun setCellContent(cellCoordinate: String, contentStr: String) {

            val coordinate = Coordinate(cellCoordinate)
            val cellOptional = spreadsheet.getCell(coordinate)
            val cell: Cell = cellOptional.orElseGet { this.createCell(coordinate) }
            var content: Content = createContent(contentStr)
            if (content is FormulaContent) {
                content.formulaProcessor = FormulaProcessor(this)
            }
            try {
                content.computeValue()
            } catch (e: FormulaException) {
                content = createContent("#ERROR")
            }
            if (hasDependantCells(cell) && content.getValue() !is NumberValue) {
               throw BadCellContentException("Updating cell with non numeric value")
            }
            cell.setNewContent(content)
            updateDependantCells(cell)
            spreadsheet.addCell(cell)
    }

    fun createFormulaProcessor(): FormulaProcessor {
        return FormulaProcessor(this)
    }

    fun createExpressionEvaluator(): PostfixEvaluator {
        return PostfixEvaluator(spreadsheet)
    }

    private fun createCell(coordinate: Coordinate): Cell {
        return Cell(coordinate)
    }

    private fun createContent(contentString: String): Content {
        with(contentString) {
         return  if (this.isNumeric()) {
               NumericalContent(contentString)
           } else if (this.isFormula()) {
               FormulaContent(contentString.drop(1))
           } else {
               TextContent(contentString)
           }

        }
    }

    fun createFunction(formulaType: String): Function? {
        return when(formulaType) {
            "SUMA" -> FunctionAdd()
            "MIN" -> FunctionMin()
            "PROMEDIO" -> FunctionMean()
            "MAX" -> FunctionMax()
            else -> null
        }
    }

    private fun updateDependantCells(cell: Cell) {
        if (cell.content !is FormulaContent) {
            return
        }
        try {
            val formulaExpression: List<Component> = (cell.content as FormulaContent).formulaExpression
            for (component in formulaExpression) {
                if (component is Coordinate) {
                    val optParentCell = spreadsheet.getCell(component)
                    val parentCell = optParentCell.orElseGet { createCell(component) }
                    checkCircularDependency(parentCell, cell)
                    parentCell.addPropertyChangeListener(cell)
                } else if (component is Function) {
                    for (arguments in component.getArguments()!!) {
                        if (arguments is Coordinate) {
                            val optParentCell = spreadsheet.getCell(arguments)
                            val parentCell =
                                optParentCell.orElseGet { createCell(arguments) }
                            checkCircularDependency(parentCell, cell)
                            parentCell.addPropertyChangeListener(cell)
                        }
                    }
                }
            }
        } catch (e : CircularDependencyException) {
            e.printError()
        }

    }

    @Throws(CircularDependencyException::class)
    private fun checkCircularDependency(parentCell: Cell, childCell: Cell) {
        val dependantCells: List<PropertyChangeListener> = childCell.getDependantCells()
        for (pcl in dependantCells) {
            val cell = pcl as Cell
            if (cell == parentCell) {
                throw CircularDependencyException(parentCell.coordinate, childCell.coordinate)
            } else {
                checkCircularDependency(cell, childCell)
            }
        }
    }

    private fun hasDependantCells(cell: Cell): Boolean {
        return cell.getDependantCells().isNotEmpty()
    }

}