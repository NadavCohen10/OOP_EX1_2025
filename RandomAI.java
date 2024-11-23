public class RandomAI extends AIPlayer {

    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Player randomAI;
        Position randomPosition;
        Disc randomDisc;
        if(isPlayerOne)
            randomAI = gameStatus.getFirstPlayer();
        else
            randomAI = gameStatus.getSecondPlayer();

        if(!gameStatus.ValidMoves().isEmpty())
        {
            randomPosition =  gameStatus.ValidMoves().get((int)(Math.random() * gameStatus.ValidMoves().size()));
            int rndmDisc = (int) (Math.random() * 3);
            if(rndmDisc == 0 && randomAI.getNumber_of_bombs()>0)
                randomDisc = new BombDisc(randomAI);
            else if (rndmDisc == 1 && randomAI.getNumber_of_unflippedable()>0)
                randomDisc = new UnflippableDisc(randomAI);
            else randomDisc = new SimpleDisc(randomAI);

            return new Move(randomPosition,randomDisc);
            }
        return null;
        }
    }

