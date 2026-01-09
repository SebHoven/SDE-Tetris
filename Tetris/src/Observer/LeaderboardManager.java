package Observer;

/**
 * LeaderboardManager - Observer that saves scores
 */
public class LeaderboardManager implements GameObserver {
    @Override
    public void update(String eventType, GameEvent data) {
        if (eventType.equals("GAME_OVER")) {
            System.out.println("📊 Leaderboard: Saving final score of " + data.getScore());
            System.out.println("   Level reached: " + data.getLevel());
            System.out.println("   Lines cleared: " + data.getLines());
        }
    }
}