package TetrisPieces;

public class LPiece extends AbstractTetrisPiece{
    public LPiece(int startX, int startY) {
        super(startX, startY);
        shape = new int[][]{
                {0, 0, 1},
                {1, 1, 1},
                {0, 0, 0}
        };
    }
}
