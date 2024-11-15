import java.util.List;

public class GameLogic implements PlayableLogic {
    private Disc [][] board = new Disc[8][8];
    private Player playerBlue;
    private Player playerRed ;
    final private int BOARD_SIZE = 8;
    // if whoIsPlaying = 1 then playerBlue else then playerRed
    private boolean whoIsPlaying = true;

    public GameLogic()
    {
        board = new Disc[BOARD_SIZE][BOARD_SIZE];
    }

    @Override
    public boolean locate_disc(Position a, Disc disc)
    {
        if (board[a.row()][a.col()]==null) {
            board[a.row()][a.col()] = disc;
            return true;
        }
        return false;
    }

    @Override
    public Disc getDiscAtPosition(Position position)
    {
        System.out.println(position.row() +" "+ position.col());

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
        System.out.println("change turn");
        whoIsPlaying = !whoIsPlaying;
        return whoIsPlaying;
        //return true;
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
        System.out.println("reset");
    }

    @Override
    public void undoLastMove() {

    }
}


