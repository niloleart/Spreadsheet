package cell.content.formula.computation

import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.expression.IFormulaExpressionFactory
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.tokens.BadTokenException
import edu.upc.etsetb.arqsoft.spreadsheet.entities.formula.tokens.IToken
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.formula.tokens.Tokenizer

class MyTokenizer {
    fun generateTokens(postfixExpression: String): List<IToken>? {
        val expressionFactory = IFormulaExpressionFactory.getInstance("DEFAULT")
        val tokenizer = Tokenizer.getInstance(expressionFactory)
        try {
            tokenizer.tokenize(postfixExpression)
            return tokenizer.tokens
        } catch (exception: BadTokenException) {
            println(exception.message)
        }
    return null
    }
}