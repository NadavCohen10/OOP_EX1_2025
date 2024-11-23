import java.util.Comparator;

public class PositionCompare implements Comparator <Position> {
    private final PlayableLogic GAME ;

    protected PositionCompare(PlayableLogic game) {
        GAME = game;
    }
    @Override
    public int compare(Position p1, Position p2) {
        if(GAME.countFlips(p1) != GAME.countFlips(p2))
          return Integer.compare(GAME.countFlips(p1) , GAME.countFlips(p2));

        if(GAME.countFlips(p1) == GAME.countFlips(p2))
            if (p1.col() == p2.col())
                return Integer.compare(p1.row(), p2.row());
        return Integer.compare(p1.col(), p2.col());
    }
}
