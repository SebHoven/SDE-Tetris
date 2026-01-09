package TetrisPieces;

public class JPiece extends AbstractTetrisPiece{
    public JPiece(int startX, int startY) {
        super(startX, startY);
        shape = new int[][]{
                {1, 0, 0},
                {1, 1, 1},
                {0, 0, 0}
        };
    }
}