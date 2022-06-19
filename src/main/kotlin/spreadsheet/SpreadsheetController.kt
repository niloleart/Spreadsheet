package spreadsheet

import cell.Cell
import cell.Coordinate
import cell.content.*
import cell.content.formula.FormulaProcessor
import cell.content.formula.computation.PostfixEvaluator
import cell.content.function.Function
import cell.content.function.FunctionAdd
import cell.content.function.FunctionMax
import cell.content.function.FunctionMean
import cell.content.function.FunctionMin
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.expression.FormulaException
import exception.BadEditCellInputException
import exception.SpreadsheetException
import exception.SpreadsheetNotInitializedException
import spreadsheet.utils.loader.CSVLoader
import spreadsheet.utils.loader.Loader
import spreadsheet.utils.saver.CSVSaver
import spreadsheet.utils.saver.Saver
import userinterface.utils.E_BAD_CELL_COORDINATES
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
            //if (hasDependantCells(cell) && content.getValue() !is NumValue) {
            //    throw BadCellContentException("Updating cell with non numeric value")
           // }
            cell.content = content
            //this.updateDependantCells(cell)
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


}