package singleton;

import Observer.GameEvent;
import Observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    // Static instance - created when class is loaded
    private static GameManager instance;

    // Game state variables
    private int score;
    private int level;
    private int linesCleared;
    private boolean isPaused;
    private static List<GameObserver> observers = new ArrayList<>();

    public static void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    // Notify all observers
    private void notifyObservers(String eventType, GameManager data) {
        for (GameObserver observer : observers) {
            observer.update(eventType, data);
        }
    }

    // Private constructor prevents instantiation from other classes
    private GameManager() {
        this.score = 0;
        this.level = 1;
        this.linesCleared = 0;
        this.isPaused = false;
    }

    // Public method to get the single instance
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // Game methods
    public void addScore(int points) {
        this.score += points;
    }

    public void clearLines(int numLines) {
        this.linesCleared += numLines;
        // Level up every 10 lines
        this.level = (this.linesCleared / 10) + 1;
    }

    public void resetGame() {
        this.score = 0;
        this.level = 1;
        this.linesCleared = 0;
        this.isPaused = false;
    }

    // Getters
    public int getScore() { return score; }
    public int getLevel() { return level; }
    public int getLinesCleared() { return linesCleared; }
    public boolean isPaused() { return isPaused; }
    public void setPaused(boolean paused) { this.isPaused = paused; }

    public void gameOver() {
    }
}

