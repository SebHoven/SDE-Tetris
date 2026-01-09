package Observer;
import singleton.GameManager;

public interface GameObserver {
    void update(String eventType, GameManager data);
}