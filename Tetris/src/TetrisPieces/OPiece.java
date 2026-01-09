package TetrisPieces;

public class OPiece extends AbstractTetrisPiece{
    public OPiece(int startX, int startY) {
        super(startX, startY);
        shape = new int[][]{
                {1, 1},
                {1, 1}
        };
    }
}
