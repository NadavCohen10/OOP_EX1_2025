import java.util.List;

public class GameLogic implements PlayableLogic {
    private Disc [][] board = new Disc[8][8];
    private Player playerBlue;
    private Player playerRed;

    final private int BOARD_SIZE = 8;
    // if whoIsPlaying = 1 then playerBlue else then playerRed
    private boolean lastPlayer = true;

    public GameLogic()
    {
        board = new Disc[BOARD_SIZE][BOARD_SIZE];
    }

    @Override
    public boolean locate_disc(Position a, Disc disc)
    {
        if (board[a.row()][a.col()]!=null)
            return false;
        switch (disc) {
            case UnflippableDisc unflippableDisc when disc.getOwner().getNumber_of_unflippedable() > 0 -> {
                disc.getOwner().reduce_unflippedable();
                board[a.row()][a.col()] = disc;
            }
            case BombDisc bombDisc when disc.getOwner().getNumber_of_bombs() > 0 -> {
                disc.getOwner().reduce_bomb();
                board[a.row()][a.col()] = disc;
            }
            case SimpleDisc simpleDisc -> board[a.row()][a.col()] = disc;
            case null, default -> {
                return false;
            }
        }
        countFlips(a);
        lastPlayer = !lastPlayer;

        return true;
    }

    @Override
    public Disc getDiscAtPosition(Position position)
    {
     //   System.out.println(position.row() +" "+ position.col());

        return board[position.row()][position.col()];

    }

    @Override
    public int getBoardSize()
    {
        return BOARD_SIZE;
    }

    @Override
    public List<Position> ValidMoves()
    {

        return List.of();
    }

    @Override
    public int countFlips(Position a)
    {
        int count = 0;
        for(int i = 1; i<8;i++)
        {
       count = count + flip(i,a.row(),a.col());

        }
        System.out.println(count);
        return count;
    }

    //לבדוק לגבי מהלכים שלא סוגרים מעגל
private int flip(int direction, int row, int col) {
    switch (direction) {
        //up
        case 1: {
            if (row - 1 > 0 && board[row - 1][col] != null && board[row - 1][col].getOwner().isPlayerOne != lastPlayer)
                return 1 + flip(direction, row - 1, col);
            break;
        }
        // up-right
        case 2: {
            if (row - 1 > 0 && col + 1 < BOARD_SIZE && board[row - 1][col + 1] != null && board[row - 1][col + 1].getOwner().isPlayerOne != lastPlayer)
                return 1 + flip(direction, row - 1, col + 1);
            break;
        }
        //right
        case 3: {
            if (col + 1 < BOARD_SIZE && board[row][col + 1] != null && board[row][col + 1].getOwner().isPlayerOne != lastPlayer)
                return 1 + flip(direction, row, col + 1);
            break;
        }
        //down-right
        case 4: {
            if (row + 1 < BOARD_SIZE && col + 1 < BOARD_SIZE && board[row + 1][col + 1] != null && board[row + 1][col + 1].getOwner().isPlayerOne != lastPlayer)
                return 1 + flip(direction, row + 1, col + 1);
            break;
        }
        //down
        case 5: {
            if (row + 1 < BOARD_SIZE && board[row + 1][col] != null && board[row + 1][col].getOwner().isPlayerOne != lastPlayer)
                return 1 + flip(direction, row + 1, col);
            break;
        }
        //down-left
        case 6: {
            if (row + 1 < BOARD_SIZE && col - 1 > 0 && board[row + 1][col - 1] != null && board[row + 1][col - 1].getOwner().isPlayerOne != lastPlayer)
                return 1 + flip(direction, row + 1, col - 1);
            break;
        }
        //left
        case 7: {
            if (col - 1 > 0 && board[row][col - 1] != null && board[row][col - 1].getOwner().isPlayerOne != lastPlayer)
                return 1 + flip(direction, row, col - 1);
            break;
        }
        //up-left
        case 8: {
            if (row - 1 > 0 && col - 1 > 0 && board[row - 1][col - 1] != null && board[row - 1][col - 1].getOwner().isPlayerOne != lastPlayer)
                return 1 + flip(direction, row - 1, col - 1);
            break;
        }

    }
    return 0;
}

    @Override
    public Player getFirstPlayer()
    {
        return playerBlue;
    }

    @Override
    public Player getSecondPlayer()
    {
        return playerRed;
    }

    @Override
    public void setPlayers(Player player1, Player player2)
    {
        playerBlue = player1;
        playerRed = player2;
        board[3][3] = new SimpleDisc(playerBlue) ;
        board[4][4] = new SimpleDisc(playerBlue) ;
        board[3][4] = new SimpleDisc(playerRed) ;
        board[4][3] = new SimpleDisc(playerRed) ;
        System.out.println("set players");

    }

    @Override
    public boolean isFirstPlayerTurn()
    {
        return lastPlayer;
    }

    @Override
    public boolean isGameFinished()
    {
        return false;
    }

    @Override
    public void reset() {
      board = new Disc[BOARD_SIZE][BOARD_SIZE];

        board[3][3] = new SimpleDisc(playerBlue) ;
        board[4][4] = new SimpleDisc(playerBlue) ;
        board[3][4] = new SimpleDisc(playerRed) ;
        board[4][3] = new SimpleDisc(playerRed);
        lastPlayer = true;
        System.out.println("reset");
    }

    @Override
    public void undoLastMove() {

    }
}


