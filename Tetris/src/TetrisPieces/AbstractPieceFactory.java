package TetrisPieces;
import java.util.Random;

public class AbstractPieceFactory implements PieceFactory{
    private static Random rand = new Random();

    @Override
    public TetrisPiece createTetrisPiece() {
        int startX = 4;
        int startY = 0;

        int choice = rand.nextInt(7);

        switch (choice) {
            case 0: return new IPiece(startX, startY);
            case 1: return new OPiece(startX, startY);
            case 2: return new TPiece(startX, startY);
            case 3: return new SPiece(startX, startY);
            case 4: return new ZPiece(startX, startY);
            case 5: return new JPiece(startX, startY);
            case 6: return new LPiece(startX, startY);
            default: throw new IllegalStateException("Unexpected value");
        }
    }
}
