package userinterface.utils

//READ COMMANDS
const val OPTION_1 = "RF"
const val TEXT_1 = "(RF <text file path>) Read commands from File"

//CREATE
const val OPTION_2 = "C"
const val TEXT_2 = "(C) Create a New spreadsheet.Spreadsheet"

//EDIT CELL
const val OPTION_3 = "E"
const val TEXT_3 = "(E <cell coordinate> <new cell content>) Edit a cell"

//LOAD
const val OPTION_4 = "L"
const val TEXT_4 = "(L <SV2 file pathname>) Load a spreadsheet from a file"

//SAVE
const val OPTION_5 = "S"
const val TEXT_5 = "(S <SV2 file pathname>) Save the spreadsheet to a file"

//EXIT
const val OPTION_6="EXIT"
const val TEXT_6 ="(EXIT) Exit the program. Remember to save your spreadsheet first."

//OTHERS
const val EXC_INVALID_INPUT = "Could not understand you! Enter a valid option, please :D\n\n"
const val TEXT_WELCOME = "Hello user, select an option and input its parameters!\n"

//OK MESSAGES
const val SPREADSHEET_CREATED = "Spreadsheet has been created!"
const val SPREADSHEET_LOADED = "Spreadsheet has been loaded!"
const val SPREADSHEET_SAVED = "Spreadsheet has been saved!"
const val SPREADSHEET_PRESENT = "Presenting spreadsheet:"

//ERRORS
const val E_NOT_INITIALIZED = "Spreadsheet has not been initialized. Create or load a spreadsheet first"
const val E_BAD_CELL_INPUT = "Input to edit cell not correct.\nEnter: E <cell coordinate> <new cell content>"
const val E_BAD_CELL_COORDINATES = "It seems that you are not entering the coordinates properly"
const val E_BAD_CELL_CONTENT = "Invalid content for cell. It can be numerical, text or a formula."
const val E_SOMETHING_WRONG = "Something went wrong."
const val E_CIRCULAR_DEPENDENCY = "Found circular dependency: "