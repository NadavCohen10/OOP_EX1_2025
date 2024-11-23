import java.util.List;
public class GreedyAI extends AIPlayer {
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Player greedyAI;
        Position AIPosition;
        Disc AIDisc;


        if(isPlayerOne)
            greedyAI = gameStatus.getFirstPlayer();
        else
            greedyAI = gameStatus.getSecondPlayer();
        if(!gameStatus.ValidMoves().isEmpty())
        {
            List<Position> AImoves = gameStatus.ValidMoves();
        }

return null;
    }
}
