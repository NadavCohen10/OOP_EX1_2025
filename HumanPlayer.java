/**
 * Represents a human player in a game.
 *
 * This class extends the Player class and overrides relevant methods
 * to indicate that this player is human.
 */
public class HumanPlayer extends Player {

    /**
     * Constructs a HumanPlayer object.
     *
     * @param isPlayerOne a boolean indicating whether this player is Player One
     *                    (true for Player One, false for PLayer Two)
     */
    public HumanPlayer(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Indicates whether this player is human.
     *
     * @return true, as this class represents a human player
     */
    @Override
    boolean isHuman() {
        return true;
    }


}
