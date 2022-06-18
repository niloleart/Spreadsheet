package cell.content

import cell.content.value.StringValue
import cell.content.value.Value

class TextContent(var contentStr: String): Content {
    private lateinit var stringValue: StringValue

    override fun getValue(): Value {
        return stringValue
    }

    override fun computeValue() {
        stringValue = StringValue(contentStr)
    }

    override fun getContentString(): String {
        return contentStr
    }
}