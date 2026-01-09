package Observer;

import singleton.GameManager;

public class LeaderboardManager implements GameObserver {
    @Override
    public void update(String eventType, GameManager data) {
        if (eventType.equals("GAME_OVER")) {
            System.out.println("Leaderboard: Saving final score of " + data.getScore());
            // Save to database or file
        }
    }
}
