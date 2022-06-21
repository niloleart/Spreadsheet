package cell.content.formula.computation

import cell.CellRange
import cell.Coordinate
import cell.content.FormulaContent
import cell.content.formula.Component
import cell.content.formula.Operator
import cell.content.function.Argument
import cell.content.function.Function
import cell.content.value.NumberValue
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.expression.IFormulaExpressionFactory
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.tokens.IToken
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.tokens.TokenType
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.formula.tokens.Token
import spreadsheet.SpreadsheetController

class ExpressionGenerator(private val spreadsheetController: SpreadsheetController) {

    fun generateExpression(formula: FormulaContent): MutableList<Component> {
        val factory = IFormulaExpressionFactory.getInstance("DEFAULT")
        val gen = factory.createExpressionGenerator("postfix", factory)
        gen.generateFromString(formula.getContentString())

        val componentList = mutableListOf<Component>()
        var iterator = gen.result.iterator()
        iterator.forEach{ token ->
            when (token.type) {
                TokenType.NUM -> componentList.add(NumberValue(token.text.toDouble()))
                TokenType.OPERATOR -> componentList.add(Operator(token.text))
                TokenType.CELL -> componentList.add(Coordinate(token.text))
                TokenType.FUNCTION -> {
                    val function: Function? = spreadsheetController.createFunction(token.text)
                    if (function != null) {
                        componentList.add(function)
                        iterator = processFunction(function, iterator)
                    }
                }
            }
        }
        return componentList
    }


    private fun processFunction(function: Function, it: Iterator<IToken>): MutableIterator<IToken> {
        var previousToken =  Token.getInstance(null,"")
        var iterator: MutableIterator<IToken> = it as MutableIterator<IToken>
        var argument: Argument? = null
        while (iterator.hasNext()) {
            var currentToken = iterator.next()
            when(currentToken.type) {
                TokenType.CLOSINGB -> {
                    iterator.remove()
                    break
                }
                TokenType.RANGE -> argument = CellRange(currentToken.text)
                TokenType.FUNCTION -> {
                    val subFunction = spreadsheetController.createFunction(currentToken.text)
                    if (subFunction != null) {
                        iterator = processFunction(subFunction, iterator)
                        function.addArgument(subFunction)
                        continue
                    }
                }
                TokenType.CELL -> argument = Coordinate(currentToken.text)
                TokenType.NUM -> {
                    var sign = 1
                    if (previousToken.text.equals("-")) {
                        sign = -1
                    }
                    argument = NumberValue(sign*currentToken.text.toDouble())
                } else -> {
                iterator.remove()
                previousToken = currentToken as Token
                continue
            }
            }
            argument?.let { arg -> function.addArgument(arg) }
        }
        return iterator

    }
}