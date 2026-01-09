package Observer;

/**
 * Observer interface for game events
 * Observers implement this to receive notifications about game state changes
 */
public interface GameObserver {
    void update(String eventType, GameEvent data);
}