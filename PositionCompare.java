import java.util.Comparator;

/**
 * A comparator for comparing Position objects based on their "flip potential"
 * and their location on the board. It is used in the context of a game
 * to prioritize moves based on the number of discs that can be flipped.
 */
public class PositionCompare implements Comparator<Position> {
    private final PlayableLogic GAME;


    /**
     * Constructs a PositionCompare object.
     *
     * @param game the game logic instance used for evaluating positions.
     */
    protected PositionCompare(PlayableLogic game) {
        GAME = game;
    }

    /**
     * Compares two positions based on their flip potential and board location.
     * <p>
     * The comparison follows these rules:
     * <ul>
     *     <li>Positions with a higher number of flips are prioritized.</li>
     *     <li>If two positions have the same flip count, they are compared by their column index.</li>
     *     <li>If the column indices are the same, they are compared by their row index.</li>
     * </ul>
     *
     * @param p1 the first position to compare.
     * @param p2 the second position to compare.
     * @return a negative integer, zero, or a positive integer if {@code p1} is less than,
     * equal to, or greater than {@code p2}.
     */
    @Override
    public int compare(Position p1, Position p2) {

        // Compare positions based on the number of flips they can generate
        if (GAME.countFlips(p1) != GAME.countFlips(p2))
            return Integer.compare(GAME.countFlips(p1), GAME.countFlips(p2));

        // If the number of flips is the same, compare positions based on column or index
        if (p1.col() == p2.col())
            return Integer.compare(p1.row(), p2.row());
        return Integer.compare(p1.col(), p2.col());
    }
}
