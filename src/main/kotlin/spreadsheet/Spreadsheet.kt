package spreadsheet

import cell.Cell
import cell.Coordinate
import cell.content.Content

class Spreadsheet(
    var cells: MutableMap<String, Content>
) : SpreadsheetInterface {

}