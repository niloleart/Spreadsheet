package cell.content.formula

import cell.content.formula.computation.Visitor

abstract class Operand : Component {
    override fun acceptVisitor(visitor: Visitor) {
        visitor.visit(this)
    }

}