package Observer;

/**
 * ScoreDisplay - Observer that displays score updates
 */
public class ScoreDisplay implements GameObserver {
    @Override
    public void update(String eventType, GameEvent data) {
        switch (eventType) {
            case "SCORE_UPDATED":
                System.out.println("💰 Score: " + data.getScore());
                break;
            case "LINES_CLEARED":
                System.out.println("🎯 Lines cleared: " + data.getLines() + " | Total Score: " + data.getScore());
                break;
            case "LEVEL_UP":
                System.out.println("⬆️ LEVEL UP! Now at level " + data.getLevel());
                break;
        }
    }
}