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

    /**
     * Zähle die Nummer von Feldern, die einen bestimmten Status besitzen
     *
     * @param status Status, der gezählt werden soll
     * @return Nummer von Feldern
     */
    public int countFieldsWithStatus(BoardStatus status) {
        int count = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (this.board[i][j] == status) {
                    count++;
                }
            }
        }

        return count;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();

        for (BoardStatus[] row : board) {
            for (BoardStatus status : row) {
                String s = "";
                switch (status) {
                    case WASSER:
                        s = "░ ";
                        break;
                    case SCHIFF:
                        s = "█ ";
                        break;
                    case VERFEHLT:
                        s = "- ";
                        break;
                    case VERSENKT:
                        s = "* ";
                        break;
                    case GETROFFEN:
                        s = "💣 ";
                        break;
                }

                output.append(s);
            }
            output.append("\n");
        }

        return output.toString();
    }
}
