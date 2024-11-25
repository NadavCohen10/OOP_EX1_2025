import java.util.List;

/**
 * Represents a Greedy AI player in the game.
 * <p>
 * The Greedy AI selects its moves based by the greatest amount of flips.
 * This class extends the AIPlayer class and overrides relevant methods.
 */
public class GreedyAI extends AIPlayer {

    /**
     * Constructs a GreedyAI player.
     *
     * @param isPlayerOne a boolean indicating whether this player is Player One
     *                    (true for Player One, false for Player Two)
     */
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Determines and returns the best move for the GreedyAI based on the current game status.
     *
     * @param gameStatus the current state of the game, providing information
     *                   about valid moves and amount of flips
     * @return the best move for the Greed AI, or null if no valid moves are available
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Player greedyAI;

        // Determine whether the AI is the first or second player
        if (isPlayerOne)
            greedyAI = gameStatus.getFirstPlayer();
        else
            greedyAI = gameStatus.getSecondPlayer();

        // Check if there are valid moves available
        if (!gameStatus.ValidMoves().isEmpty()) {
            List<Position> AImoves = gameStatus.ValidMoves();

            // Sort the moves using a custom comparator (PositionCompare)
            AImoves.sort(new PositionCompare(gameStatus));

            // Return the move with the greatest amount of flips as determined by the comparator
            return new Move(AImoves.getLast(), new SimpleDisc(greedyAI));
        }

        return null;
    }
}
