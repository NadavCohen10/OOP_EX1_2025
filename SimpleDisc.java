/**
 * Represents a Simple Disc in the game.
 *
 * A Simple Disc is a basic type of disc that has an owner and can have its owner changed.
 */
public class SimpleDisc implements Disc {

    // The player who currently owns this Simple Disc
    private Player currentPlayer;

    /**
     * Constructs a SimpleDisc object with the specified owner.
     *
     * @param currentPlayer the player who owns this Simple Disc
     */
    public SimpleDisc(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    /**
     * Retrieves the owner of this Simple Disc.
     *
     * @return the player who currently owns this Simple Disc
     */
    @Override
    public Player getOwner() {
        return currentPlayer;
    }

    /**
     * Sets a new owner for this Simple Disc.
     *
     * @param player the player to be set as the new owner of this Simple Disc
     */
    @Override
    public void setOwner(Player player) {
        currentPlayer = player;
    }

    /**
     * Retrieves the type of this Simple Disc.
     *
     * @return the type string representing this Simple Disc ("⬤")
     */
    @Override
    public String getType() {

        return "⬤";
    }
}
