package TetrisPieces;

public class SPiece extends AbstractTetrisPiece {
    public SPiece(int startX, int startY) {
        super(startX, startY);
        shape = new int[][]{
                {0, 1, 1},
                {1, 1, 0},
                {0, 0, 0}
        };
    }
}
