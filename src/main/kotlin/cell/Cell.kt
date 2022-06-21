package cell

import cell.content.Content
import cell.content.FormulaContent
import cell.content.function.Argument
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

class Cell(
    var coordinate: Coordinate
) : Argument, PropertyChangeListener {
    lateinit var content: Content
    var propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)

    private fun removePropertyChangeListener(pcl: PropertyChangeListener?) {
        propertyChangeSupport.removePropertyChangeListener(pcl)
    }

    fun addPropertyChangeListener(pcl: PropertyChangeListener?) {
        propertyChangeSupport.addPropertyChangeListener(pcl)
    }

    fun getDependantCells(): List<PropertyChangeListener> {
        return propertyChangeSupport.propertyChangeListeners.asList()
    }

    fun setNewContent(content: Content) {
        this.content = content
        propertyChangeSupport.firePropertyChange("content", null, this)
    }

    override fun propertyChange(evt: PropertyChangeEvent?) {
        if (evt != null) {
                if (evt.propertyName.equals("content")) {
                    val cell: Cell = evt.newValue as Cell
                    val newContent: Content = content
                    if (content is FormulaContent) {
                        var existsCellInFormula = (newContent as FormulaContent).formulaExpression.contains(cell.coordinate)
                        newContent.formulaExpression.forEach { component ->
                            if (component is cell.content.function.Function) {
                                existsCellInFormula  = existsCellInFormula or (component.getArguments()?.contains(cell.coordinate)
                                    ?: false)
                            }
                        }
                        if (existsCellInFormula) {
                            newContent.computeValue(true)
                            setNewContent(newContent)
                        } else {
                            cell.removePropertyChangeListener(this)
                        }
                    } else {
                        cell.removePropertyChangeListener(this)
                    }
            }
        }
    }
}