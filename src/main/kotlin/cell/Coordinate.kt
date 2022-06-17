package cell

class Coordinate(
    var row: Int,
    var column: String,
    private var coordinates: String
) {

    fun getCoordinates() = row.toString() + column

    fun setCoordinates(coordinate: Coordinate) {
        val splitCoordinates = coordinate.toString().chunked(1)
        row = splitCoordinates[0].toInt()
        column = splitCoordinates[1]
    }
}