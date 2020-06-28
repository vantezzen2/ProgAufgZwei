package consoleGrammar;

import java.io.IOException;
import java.io.InputStream;

public class ConsoleGrammar implements Grammar {
    ConsoleGrammarStatus status;

    private boolean parseWord(String word) {
        if (status == ConsoleGrammarStatus.START) {
            if (word.equals("login")) {
                status = ConsoleGrammarStatus.CMD;
                return true;
            }
        } else if (status == ConsoleGrammarStatus.CMD) {
            if (word.equals("ls")) {
                return true;
            } else if (word.equals("cd")) {
                status = ConsoleGrammarStatus.ARGS;
                return true;
            } else if (word.equals("logout")) {
                status = ConsoleGrammarStatus.LOGOUT;
                return true;
            }
        } else if (status == ConsoleGrammarStatus.ARGS) {
            status = ConsoleGrammarStatus.CMD;
            return true;
        }

        // Illegal Command or status
        System.out.println("Invalid command '" + word + "' in status " + status.toString());
        return false;
    }

    @Override
    public boolean isValid(InputStream is) throws IOException {
        status = ConsoleGrammarStatus.START;

        // Lese Daten von dem InputStream
        String input = new String(is.readAllBytes());

        // Spalte die einzelnen Wörter der Eingabe
        String[] words = input.split("[\n ]");

        System.out.println("Parsing " + words.length + " words");

        for (String word : words) {
            boolean s = this.parseWord(word.trim());

            if (!s) {
                return false;
            }
        }

        // Has to end with logged out user
        return status == ConsoleGrammarStatus.LOGOUT;
    }
}
