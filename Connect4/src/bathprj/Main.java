package bathprj;

/*
Main
---------------------------
This class runs a board game (currently connect4)
*/
public class Main {

    private IBoardGame boardGame;

    public void run() {
	boardGame = new Connect4();
	boardGame.startGame();
    }

    public static void main(String[] args) {
	Main mainGame = new Main(); 
	mainGame.run();
    }

}
