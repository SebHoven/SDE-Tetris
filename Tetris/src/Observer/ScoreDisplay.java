package Observer;

import singleton.GameManager;

public class ScoreDisplay implements GameObserver {
    @Override
    public void update(String eventType, GameManager data) {
        if (eventType.equals("LINES_CLEARED")) {
            System.out.println("Score Display: Score updated to " + data.getScore());
            // Update UI component here
        }
    }
}