public class UnflippableDisc implements Disc{

    final private Player CURRENT_PLAYER;
    public UnflippableDisc(Player currentPlayer) {
        CURRENT_PLAYER = currentPlayer;
    }

    @Override
    public Player getOwner() {
        return CURRENT_PLAYER;
    }

    @Override
    public void setOwner(Player player) {
        return;

    }

    @Override
    public String getType()
    {
        return "UD" ;
      //  return "⭕" ;
    }
}
