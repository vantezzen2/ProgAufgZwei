package consoleGrammar;

import java.io.IOException;
import java.io.InputStream;

public interface Grammar {
    boolean isValid(InputStream is) throws IOException;
}
