package Observer;

/**
 * Observer interface for game tick events
 * Objects that want to respond to automatic game ticks implement this
 */
public interface TickObserver {
    /**
     * Called when a game tick occurs (for automatic falling)
     */
    void onTick();

    /**
     * Called when the tick speed changes (level up)
     */
    void onSpeedChange(long newTickSpeed);
}