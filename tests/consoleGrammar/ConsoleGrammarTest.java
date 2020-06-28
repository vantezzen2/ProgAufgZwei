package consoleGrammar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleGrammarTest {
    private InputStream stringToStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    @Test
    @DisplayName("Can identify valid grammar")
    void canIdentifyValidGrammar() throws IOException {
        Grammar gr = new ConsoleGrammar();

        // Valid tests
        boolean test1 = gr.isValid(this.stringToStream(
                "login\nlogout"
        ));
        assertTrue(test1);

        boolean test2 = gr.isValid(this.stringToStream(
                "login\nls\nlogout"
        ));
        assertTrue(test2);

        boolean test3 = gr.isValid(this.stringToStream(
                "login\ncd test\nlogout"
        ));
        assertTrue(test3);

        boolean test4 = gr.isValid(this.stringToStream(
                "login\nls\nls\ncd test\nls\ncd ls\nls\nlogout"
        ));
        assertTrue(test4);

        // Invalid tests
        boolean itest1 = gr.isValid(this.stringToStream(
                "login"
        ));
        assertFalse(itest1);

        boolean itest2 = gr.isValid(this.stringToStream(
                "login\nlogou"
        ));
        assertFalse(itest2);

        boolean itest3 = gr.isValid(this.stringToStream(
                "login\nls test\nlogout"
        ));
        assertFalse(itest3);

        boolean itest4 = gr.isValid(this.stringToStream(
                "login\ncd\nlogout"
        ));
        assertFalse(itest4);

        boolean itest5 = gr.isValid(this.stringToStream(
                ""
        ));
        assertFalse(itest5);
    }
}