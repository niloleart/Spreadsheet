package userinterface

import cell.Coordinate

interface TextBasedUserInterface {
    fun readCommandFromFile(path: String)
    fun createSpreadsheet()
    fun editCell()
    fun loadSpreadsheet(path: String)
    fun saveSpreadSheet(path: String)
    fun printSpreadsheet()
}