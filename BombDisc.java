/**
 * Represents a Bomb Disc in the game.
 * <p>
 * A Bomb Disc is a type of disc with unique behavior.
 */
public class BombDisc implements Disc {

    // The player who currently owns this Bomb Disc
    private Player currentPlayer;

    /**
     * Constructs a BombDisc object with the specified owner.
     *
     * @param currentPlayer the player who owns this Bomb Disc
     */
    public BombDisc(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Retrieves the owner of this Bomb Disc.
     *
     * @return the player who currently owns this Bomb Disc
     */
    @Override
    public Player getOwner() {
        return currentPlayer;
    }

    /**
     * Sets a new owner for this Bomb Disc.
     *
     * @param player the player to be set as the new owner of this Bomb Disc
     */
    @Override
    public void setOwner(Player player) {
        currentPlayer = player;
        ;

    }

    /**
     * Retrieves the type of this Bomb Disc.
     *
     * @return the type string representing this Bomb Disc ("💣")
     */
    @Override
    public String getType() {
        return "💣";
    }
}
