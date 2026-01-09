package TetrisPieces;

public class TPiece extends AbstractTetrisPiece {
    public TPiece(int startX, int startY) {
        super(startX, startY);
        shape = new int[][] {
                {0,0,0},
                {1,1,1},
                {0,1,0}
        };
    }
}
