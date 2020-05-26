package schiffeversenken.Board;

/**
 * Schiffe versenken Spielfeld
 * <p>
 * Das Spielfeld besteht aus einem 10x10 Grid mit Werten aus BoardStatus
 */
public class Board {
    private final BoardStatus[][] board;

    public Board() {
        this.board = new BoardStatus[10][10];

        this.reset();
    }

    /**
     * Setzte das Spielfeld zurück auf Wasser
     */
    public void reset() {
        // Setze alle Koordinaten auf WASSER
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.board[i][j] = BoardStatus.WASSER;
            }
        }
    }

    /**
     * Setze den Status einer Koordinate des Spielfeldes
     *
     * @param x
     * @param y
     * @param status
     */
    public void set(int x, int y, BoardStatus status) {
        board[x][y] = status;
    }

    /**
     * Hole den aktuellen Status einer Koordinate des Spielfeldes
     *
     * @param x
     * @param y
     * @return
     */
    public BoardStatus get(int x, int y) {
        return board[x][y];
    }
}
