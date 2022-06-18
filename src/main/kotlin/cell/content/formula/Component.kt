package cell.content.formula

import cell.content.formula.computation.Visitor

interface Component {
    fun acceptVisitor(visitor: Visitor)
}