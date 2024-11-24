/**
 * Represents an Unflippable Disc in the game.
 *
 * An Unflippable Disc is a type of disc that cannot have its owner changed
 * once it is set, which is represented by the final field for the current player.
 */
public class UnflippableDisc implements Disc{


    // The player who currently owns this Unflippable Disc
    final private Player CURRENT_PLAYER;

    /**
     * Constructs an UnflippableDisc object with the specified owner.
     *
     * @param currentPlayer the player who owns this Unflippable Disc
     */
    public UnflippableDisc(Player currentPlayer) {
        CURRENT_PLAYER = currentPlayer;
    }

    /**
     * Retrieves the owner of this Unflippable Disc.
     *
     * @return the player who currently owns this Unflippable Disc
     */
    @Override
    public Player getOwner() {
        return CURRENT_PLAYER;
    }

    /**
     * This method does nothing because the Unflippable Disc's owner cannot be changed.
     *
     * @param player the player to set as the new owner (this is ignored)
     */
    @Override
    public void setOwner(Player player) {
        return;
    }
    /**
     * Retrieves the type of this Unflippable Disc.
     *
     * @return the type string representing this Unflippable Disc ("⭕")
     */
    @Override
    public String getType()
    {
        return "UD" ;
      //  return "⭕" ;
    }
}
