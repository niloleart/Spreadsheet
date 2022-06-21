
import userinterface.UserInterface
import userinterface.utils.COLOR_GREEN
import userinterface.utils.COLOR_ORIGINAL
import userinterface.utils.E_SOMETHING_WRONG
import java.io.File
import java.util.*

var userInterface: UserInterface = UserInterface()
var RF_MODE = false
lateinit var scanner: Scanner
var arguments: Array<String>? = null

fun main(args: Array<String>? = null) {
    userInterface.showOptions()
    if (args != null && args.isNotEmpty() || RF_MODE) {
        shouldInitScannerForRF(args)
        processRFInputs(args)
    } else {
        processSystemInput(args)
    }
}

private fun processSystemInput(args: Array<String>?) {
    scanner = Scanner(System.`in`)
    val userInput = scanner.nextLine()
    processUserInput(userInput)
    main(args)
}

private fun processRFInputs(args: Array<String>?) {
    while (scanner.hasNextLine()) {
        val userInput = scanner.nextLine()

        if (userInput.isNotEmpty()) {
            println(COLOR_GREEN+userInput+ COLOR_ORIGINAL )
            processUserInput(userInput)
            main(args)
        } else {
            arguments = null
            RF_MODE = false
        }
    }
}

private fun shouldInitScannerForRF(args: Array<String>?) {
    if (!RF_MODE) {
        initArgs(args)
        scanner = Scanner(File(args!![0]))
    }
}

private fun initArgs(args: Array<String>?) {
    RF_MODE = true
    arguments = args
}

private fun processUserInput(userInput: String?) {
    userInput?.let {
        userInterface.processInput(userInput)
    } ?: run {
        println(E_SOMETHING_WRONG)
    }

}
