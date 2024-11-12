import java.util.List;

public class GameLogic implements PlayableLogic {
    private Disc [][] board = new Disc[8][8];
    private Player playerBlue;
    private Player playerRed ;

    public GameLogic()
    {
        board = new Disc[8][8];

//        board[3][3] = new SimpleDisc(playerBlue) ;
//        board[4][4] = new SimpleDisc(playerBlue) ;
//        board[3][4] = new SimpleDisc(playerRed) ;
//        board[4][3] = new SimpleDisc(playerRed) ;
    }

    @Override
    public boolean locate_disc(Position a, Disc disc) {
        return false;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
        return board[position.row()][position.col()];
    }

    @Override
    public int getBoardSize() {
        return 8;
    }

    @Override
    public List<Position> ValidMoves() {
        return List.of();
    }

    @Override
    public int countFlips(Position a) {
        return 0;
    }

    @Override
    public Player getFirstPlayer() {
        return playerBlue;
    }

    @Override
    public Player getSecondPlayer() {
        return playerRed;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {
        playerBlue = player1;
        playerRed = player2;
        board[3][3] = new SimpleDisc(playerBlue) ;
        board[4][4] = new SimpleDisc(playerBlue) ;
        board[3][4] = new SimpleDisc(playerRed) ;
        board[4][3] = new SimpleDisc(playerRed) ;
        System.out.println("aaaaaa");

    }

    @Override
    public boolean isFirstPlayerTurn() {
        return true;
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void undoLastMove() {

    }
}


