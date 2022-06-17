package spreadsheet

import cell.content.setContent
import exception.BadEditCellInputException
import exception.InvalidCellContentException
import exception.SpreadsheetException
import exception.SpreadsheetNotInitializedException
import spreadsheet.utils.loader.CSVLoader
import spreadsheet.utils.loader.Loader
import spreadsheet.utils.saver.CSVSaver
import spreadsheet.utils.saver.Saver
import userinterface.utils.E_BAD_CELL_COORDINATES

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
        spreadsheet = Spreadsheet(mutableMapOf())
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
            setValue(userInput[1], input)
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

    private fun setValue(cellCoordinate: String, content: String) {
        try {
            spreadsheet.cells[cellCoordinate.uppercase()] = content.setContent()
        } catch (exception: InvalidCellContentException) {
            throw exception
        }

    }


}