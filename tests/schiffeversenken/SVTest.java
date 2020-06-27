package schiffeversenken;

import org.junit.jupiter.api.Test;
import schiffeversenken.Board.BoardStatus;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SVTest {
    @Test
    public void usageTest() throws IOException, StatusException {
        // Initialize games
        DummySender sender1 = new DummySender();
        Engine game1 = new Engine(sender1);

        DummySender sender2 = new DummySender();
        Engine game2 = new Engine(sender2);

        // Connect dummy senders
        sender1.setReceiver(game2);
        sender2.setReceiver(game1);

        // Test do dice
        game1.doDice();
        game2.doDice();

        Engine activeGame = game1.isActive() ? game1 : game2;
        Engine passiveGame = game1.isActive() ? game2 : game1;

        assertFalse(passiveGame.isActive());

        // Throw bomb
        activeGame.bombe_werfen(0, 0);

        assertNotEquals(BoardStatus.WASSER, activeGame.getOtherBoard().get(0, 0));
    }
}