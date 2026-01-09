package TetrisPieces;

public class ZPiece extends AbstractTetrisPiece{
    public ZPiece(int startX, int startY) {
        super(startX, startY);
        shape = new int[][]{
                {1, 1, 0},
                {0, 1, 1},
                {0, 0, 0}
        };
    }
}
