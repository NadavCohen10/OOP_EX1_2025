public class SimpleDisc implements Disc {
    private Player currentPlayer;
    public SimpleDisc(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public Player getOwner() {
        return currentPlayer;
    }

    @Override
    public void setOwner(Player player) {
        currentPlayer = player;

    }

    @Override
    public String getType() {

        return "⬤";
    }
}
