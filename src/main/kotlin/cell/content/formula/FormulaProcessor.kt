package cell.content.formula

import cell.CellRange
import cell.Coordinate
import cell.content.FormulaContent
import cell.content.formula.computation.MyTokenizer
import cell.content.formula.evaluators.ExpressionEvaluator
import cell.content.function.Argument
import cell.content.function.Function
import cell.content.value.NumberValue
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.expression.IFormulaExpressionFactory
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.tokens.IToken
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.tokens.TokenType
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.formula.tokens.Token
import spreadsheet.SpreadsheetController

class FormulaProcessor(var spreadsheetController: SpreadsheetController) {
    private lateinit var formula: FormulaContent
    private var tokens: List<IToken>? = null
    var expression: List<Component> = mutableListOf()
    private lateinit var numberValue: NumberValue


    fun computeFormulaValue(formula: FormulaContent): NumberValue {
        this.formula = formula

        //1. Tokenize
        tokenizeFormula()

        //TODO 2. Parsing(tokens) mira sintaxis, retorna tokens si no hi ha problemes. Esta fet al que ens ha passat
        parseFormula()

        //3. Generate Postifx
        generateExpression()

        //4. Evaluate postfix(postfix)
        evaluatePostfix()

        return numberValue
    }

    private fun tokenizeFormula() {
        val tokenizer = MyTokenizer()
        tokens = tokenizer.generateTokens(formula.getContentString())
    }

    private fun parseFormula() {

    }

    private fun generateExpression() {
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
        expression = componentList.toList()
    }


    private fun evaluatePostfix() {
        val evaluator: ExpressionEvaluator = spreadsheetController.createExpressionEvaluator()
        numberValue = evaluator.evaluateExpression(expression)
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
        }
        argument?.let { arg -> function.addArgument(arg) }
        return iterator

    }

}