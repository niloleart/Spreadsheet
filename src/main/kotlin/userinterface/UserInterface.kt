package userinterface

import exception.BadEditCellInputException
import exception.InvalidCellContentException
import exception.SpreadsheetNotInitializedException
import main
import spreadsheet.SpreadsheetController
import userinterface.utils.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.Scanner
import kotlin.system.exitProcess

class UserInterface : TextBasedUserInterface {

    var firstEntry = true
    var READING_FILE_MODE = false
    private val optionList = listOf(OPTION_1, OPTION_2, OPTION_3, OPTION_4, OPTION_5, OPTION_6)
    private val textList = listOf(TEXT_1, TEXT_2, TEXT_3, TEXT_4, TEXT_5, TEXT_6)
    private lateinit var userInput: List<String>
    private val spreadsheetController = SpreadsheetController()

    fun showOptions() {
        println("$COLOR_MAGENTA---------------------------------------------------------------")
        if (firstEntry) {
            println(TEXT_WELCOME)
            firstEntry = false
        }
        for (text in textList) {
            println(text)
        }
        println("---------------------------------------------------------------$COLOR_ORIGINAL")
    }

    fun processInput(input: String) {
        userInput = input.split(" ")
        if (optionList.contains(userInput[0].uppercase())) {
            processOption(userInput[0].uppercase())
        } else {
            processError()
        }
       // main()
    }

    private fun processError() {
        println(COLOR_RED+EXC_INVALID_INPUT+ COLOR_ORIGINAL)

    }

    private fun processOption(option: String) {
        when (option) {
            OPTION_1 -> {
                //TODO
                val path = "../../Desktop/rf.txt"
               //readCommandFromFile(userInput[1])
                readCommandFromFile(path)
            }
            OPTION_2 -> {
                createSpreadsheet()
            }
            OPTION_3 -> {
                editCell()
            }
            OPTION_4 -> {
                loadSpreadsheet(userInput[1])
            }
            OPTION_5 -> {
                //TODO
                val path = "../../Desktop/prova1.csv"
                //saveSpreadSheet(userInput[1])
                saveSpreadSheet(path)
            }
            OPTION_6 -> {
                exitProcess(0)
            }
            else -> {
                processError()
            }
        }
        printSpreadsheet()
    }


    override fun readCommandFromFile(path: String) {
        main(arrayOf(path))
    }

    override fun createSpreadsheet() {
        spreadsheetController.create()
        println(COLOR_GREEN + SPREADSHEET_CREATED + COLOR_ORIGINAL+"\n")
    }

    override fun editCell() {
        try {
            spreadsheetController.editCell(userInput)
        } catch (exception: SpreadsheetNotInitializedException) {
            exception.printError()
        } catch (exception: BadEditCellInputException) {
            exception.printError()
        } catch (exception: InvalidCellContentException) {
            exception.printError()
        }
    }

    override fun loadSpreadsheet(path: String) {
        spreadsheetController.loadSpreadsheet(path)
        println(COLOR_GREEN + SPREADSHEET_LOADED + COLOR_ORIGINAL+"\n")
    }

    override fun saveSpreadSheet(path: String) {
        try {
            spreadsheetController.saveSpreadsheet(path)
            println(COLOR_GREEN + SPREADSHEET_SAVED + COLOR_ORIGINAL+"\n")
        } catch (exception: SpreadsheetNotInitializedException) {
            exception.printError()
        }

    }

    override fun printSpreadsheet() {
        with(spreadsheetController) {
            if (this.isSpreadsheetInitialized() && this.spreadsheet.cells.isNotEmpty()) {
                println("$COLOR_BLUE**************************")
                println(SPREADSHEET_PRESENT)
                for ((key, value) in this.spreadsheet.cells) {
                    println(key+","+ value.value.getAsString())
                }
                println("**************************$COLOR_ORIGINAL\n")
            }

        }



    }
}