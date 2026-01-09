package Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * GameTimer is the Subject in the Observer pattern
 * It notifies all observers when it's time for pieces to fall
 */
public class GameTimer {
    private List<TickObserver> observers;
    private Timer timer;
    private long tickSpeed; // milliseconds between ticks
    private boolean running;

    public GameTimer(long initialTickSpeed) {
        this.observers = new ArrayList<>();
        this.tickSpeed = initialTickSpeed;
        this.running = false;
    }

    /**
     * Register an observer to be notified on each tick
     */
    public void addObserver(TickObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove an observer
     */
    public void removeObserver(TickObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify all observers that a tick has occurred
     */
    private void notifyTick() {
        for (TickObserver observer : observers) {
            observer.onTick();
        }
    }

    /**
     * Notify all observers that the speed has changed
     */
    private void notifySpeedChange() {
        for (TickObserver observer : observers) {
            observer.onSpeedChange(tickSpeed);
        }
    }

    /**
     * Start the game timer
     */
    public void start() {
        if (running) {
            return;
        }

        running = true;
        timer = new Timer();
        scheduleNextTick();
    }

    /**
     * Schedule the next tick
     */
    private void scheduleNextTick() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    notifyTick();
                    scheduleNextTick(); // Schedule next tick
                }
            }
        }, tickSpeed);
    }

    /**
     * Stop the game timer
     */
    public void stop() {
        running = false;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Pause the timer
     */
    public void pause() {
        running = false;
    }

    /**
     * Resume the timer
     */
    public void resume() {
        if (!running) {
            start();
        }
    }

    /**
     * Change the tick speed (e.g., when leveling up)
     */
    public void setTickSpeed(long newTickSpeed) {
        this.tickSpeed = newTickSpeed;
        notifySpeedChange();

        // Restart timer with new speed
        if (running) {
            stop();
            start();
        }
    }

    /**
     * Get current tick speed
     */
    public long getTickSpeed() {
        return tickSpeed;
    }

    /**
     * Check if timer is running
     */
    public boolean isRunning() {
        return running;
    }
}