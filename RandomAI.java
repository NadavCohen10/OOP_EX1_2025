public class RandomAI extends AIPlayer {

    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Player plyrAIrdm;
        Position randomPosition;
        Disc randomDisc;
        if(isPlayerOne)
            plyrAIrdm = gameStatus.getFirstPlayer();
        else
            plyrAIrdm = gameStatus.getSecondPlayer();

        if(!gameStatus.ValidMoves().isEmpty())
        {
            randomPosition =  gameStatus.ValidMoves().get((int)(Math.random() * gameStatus.ValidMoves().size()));
            int rndmDisc = (int) (Math.random() * 3);
            if(rndmDisc == 0 && plyrAIrdm.getNumber_of_bombs()>0)
                randomDisc = new BombDisc(plyrAIrdm);
            else if (rndmDisc == 1 && plyrAIrdm.getNumber_of_unflippedable()>0)
                randomDisc = new UnflippableDisc(plyrAIrdm);
            else randomDisc = new SimpleDisc(plyrAIrdm);

            return new Move(randomPosition,randomDisc);
            }
        return null;
        }
    }

