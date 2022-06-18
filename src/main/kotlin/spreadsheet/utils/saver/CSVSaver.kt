package spreadsheet.utils.saver

import spreadsheet.Spreadsheet
import java.io.FileWriter
import java.io.IOException


class CSVSaver : Saver {

    override fun save(path: String, spreadsheet: Spreadsheet) {
        val eol = System.getProperty("line.separator")
//TODO
        try {
            /**
            FileWriter(path).use { writer ->
                for ((key, value) in spreadsheet.cells.entries) {
                    writer.append(key.toAlphabetic())
                        .append(',')
                        .append(value.value.getAsString())
                        .append(eol)
                }
            }
            */
        } catch (ex: IOException) {
            ex.printStackTrace(System.err)
        }


        }


}
