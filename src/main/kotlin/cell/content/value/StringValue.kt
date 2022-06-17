package cell.content.value

class StringValue(private val value: String) : Value{
    override fun getAsDouble(): Double {
        return value.toDouble()
    }

    override fun getAsString(): String {
        return value
    }
}