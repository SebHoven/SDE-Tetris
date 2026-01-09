package Observer;

/**
 * LevelSpeedObserver watches for level changes and adjusts game speed
 * This demonstrates chaining observers - observing GameManager to control GameTimer
 */
public class LevelSpeedObserver implements GameObserver {
    private GameTimer gameTimer;
    private long baseTickSpeed; // Starting speed in milliseconds
    private int lastLevel;

    public LevelSpeedObserver(GameTimer gameTimer, long baseTickSpeed) {
        this.gameTimer = gameTimer;
        this.baseTickSpeed = baseTickSpeed;
        this.lastLevel = 1;
    }

    @Override
    public void update(String eventType, GameEvent data) {
        // When level changes, increase game speed
        if (eventType.equals("LEVEL_UP")) {
            int newLevel = data.getLevel();

            if (newLevel > lastLevel) {
                // Speed increases by 10% per level (pieces fall faster)
                long newSpeed = calculateSpeedForLevel(newLevel);
                gameTimer.setTickSpeed(newSpeed);
                lastLevel = newLevel;

                System.out.println("📈 Level " + newLevel + " - Speed: " + newSpeed + "ms");
            }
        } else if (eventType.equals("GAME_OVER")) {
            // Stop timer when game is over
            gameTimer.stop();
        } else if (eventType.equals("GAME_RESET")) {
            // Reset to base speed
            lastLevel = 1;
            gameTimer.setTickSpeed(baseTickSpeed);
            gameTimer.start();
        }
    }

    /**
     * Calculate tick speed based on level
     * Higher levels = faster falling (lower tick speed)
     */
    private long calculateSpeedForLevel(int level) {
        // Speed increases 10% per level
        double speedMultiplier = Math.pow(0.9, level - 1);
        long newSpeed = (long)(baseTickSpeed * speedMultiplier);

        // Minimum speed cap at 100ms (very fast!)
        return Math.max(100, newSpeed);
    }
}