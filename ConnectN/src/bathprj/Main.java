package bathprj;

/*
Main
---------------------------
This class runs a board game (currently connectN)
*/
public class Main {

    private IBoardGame boardGame;

    public void run() {
	boardGame = new ConnectN();
	boardGame.startGame();
    }

    public static void main(String[] args) {
	Main mainGame = new Main(); 
	mainGame.run();
    }

}
