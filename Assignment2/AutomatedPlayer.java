public class AutomatedPlayer extends Player {

    public AutomatedPlayer(PlayerMap gameMap) {
        super(gameMap);
    }

    @Override
    public void takeTurn() {

    }

    @Override
    public void selectArea() {

    }

    @Override
    public void close() {
        // Cleanup anything you use here
        // HumanPlayer uses this to close its scanner
    }

}
