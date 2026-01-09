package Observer;

/**
 * GameEvent - Data object passed to observers
 * Contains information about game state changes
 */
public class GameEvent {
    private int score;
    private int lines;
    private int level;

    public GameEvent(int score, int lines, int level) {
        this.score = score;
        this.lines = lines;
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public int getLines() {
        return lines;
    }

    public int getLevel() {
        return level;
    }
}