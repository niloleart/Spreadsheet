package cell.content.formula

import cell.content.FormulaContent
import cell.content.formula.computation.MyTokenizer
import cell.content.formula.evaluators.ExpressionEvaluator
import cell.content.value.NumberValue
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.expression.IFormulaExpressionFactory
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.syntax.SyntaxException
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.tokens.IToken
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.formula.syntax.SyntaxChecker
import spreadsheet.SpreadsheetController

class FormulaProcessor(var spreadsheetController: SpreadsheetController) {
    private lateinit var formula: FormulaContent
    private var tokens: List<IToken>? = null
    var expression: List<Component> = mutableListOf()
    private lateinit var numberValue: NumberValue


    fun computeFormulaValue(formula: FormulaContent): NumberValue {
        this.formula = formula

        tokenizeFormula()
        parseFormula()
        generateExpression()
        evaluatePostfix()

        return numberValue
    }

    private fun tokenizeFormula() {
        val tokenizer = MyTokenizer()
        tokens = tokenizer.generateTokens(formula.getContentString())
    }

    private fun parseFormula() {
        val syntaxChecker = SyntaxChecker.getInstance(IFormulaExpressionFactory.getInstance("DEFAULT"))
        try {
            syntaxChecker.check(tokens.toString())
        } catch (_: SyntaxException) {

        }
    }

    private fun generateExpression() {
        val expressionGenerator = spreadsheetController.createExpressionGenerator()
        expression = expressionGenerator.generateExpression(formula)
    }


    private fun evaluatePostfix() {
        val evaluator: ExpressionEvaluator = spreadsheetController.createExpressionEvaluator()
        numberValue = evaluator.evaluateExpression(expression)
    }

    fun recomputeFormulaValue(formula: FormulaContent): NumberValue {
        this.formula = formula
        expression = formula.formulaExpression
        evaluatePostfix()
        return numberValue
    }

}