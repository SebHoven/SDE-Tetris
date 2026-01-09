package TetrisPieces;

public class IPiece extends AbstractTetrisPiece {
    public IPiece(int startX, int startY) {
        super(startX, startY);
        shape = new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
    }
}
