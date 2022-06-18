package cell.content.formula

import cell.Cell
import cell.Coordinate
import cell.content.FormulaContent
import cell.content.formula.computation.MyTokenizer
import cell.content.formula.computation.PostfixEvaluator
import cell.content.formula.evaluators.ExpressionEvaluator
import cell.content.value.NumberValue
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.expression.IFormulaExpressionFactory
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.tokens.IToken
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.tokens.TokenType
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.formula.tokens.Token
import spreadsheet.SpreadsheetController

class FormulaProcessor(var spreadsheetController: SpreadsheetController) {
    private lateinit var formula: FormulaContent
    private  var tokens: List<IToken>? = null
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

        //TODO 4. Evaluate postfix(postfix)
        evaluatePostfix()

        return numberValue
        //return FormulaContent(stringValue, operators, operands, NumberValue())
    }



   private fun tokenizeFormula() {
       val tokenizer = MyTokenizer()
       tokens = tokenizer.generateTokens(formula.getContentString())
   }

    private fun generateExpression() {
        val factory = IFormulaExpressionFactory.getInstance("DEFAULT")
        val gen = factory.createExpressionGenerator("postfix", factory)
        gen.generateFromString(formula.getContentString())

        val componentList = mutableListOf<Component>()

        for (c in gen.result) {
           when(c.type) {
               TokenType.NUM -> componentList.add(NumberValue(c.text.toDouble()))
               TokenType.OPERATOR -> {
                   componentList.add(Operator(c.text))

               }
               TokenType.CELL -> componentList.add(Coordinate(c.text))

           }
        }
        expression = componentList.toList()
    }

    private fun parseFormula() {

    }

    private fun evaluatePostfix() {
        val evaluator: ExpressionEvaluator = spreadsheetController.createExpressionEvaluator()
        numberValue = evaluator.evaluateExpression(expression)
    }

}