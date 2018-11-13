package bathprj;

import java.util.ArrayList;
import java.util.List;

import bathprj.controller.*;
import bathprj.view.*;

/*
ConnectN
---------------------------
This Class is for a connectN board game implementation
*/
public class ConnectN implements IBoardGame, IConnectN {

    List<Agent> agents;
    IConnectNDisplay display;
    IBoard board;
    IConnectNController cNController;

    /*
    Constructor
    */
    public ConnectN() {
	agents = new ArrayList<Agent>();
	agents.add(new Human("Red Player", 1));
	agents.add(new Bot("Yellow Bot", 2));
	agents.add(new Bot("Blue Bot", 3));
	board = new Board(6, 7, agents.size());
	display = new TextBoardDisplay();
	cNController = new TextConnectNController();
    }

    /*
    	Start Game
	---------------------------
	This method starts the connectN game.
    */
    @Override
    public void startGame() {
	cNController.startScreen();
	cNController.options(this);
	display.displayBoard(board.getBoard());
	mainLoop();
    }

    /*
	main Loop
	---------------------------
	This method runs the main game loop for the connectN game.
     */
    public void mainLoop() {

	boolean continueTheGame = true;

	while (continueTheGame) {
	    for (int i = 0; i < agents.size(); i++) {
		//get the next move from the current agent in the list
		int nextMove = agents.get(i).getAgentController().getMove(this);

		//if the move is valid, make the move.
		if (board.canDrop(nextMove)) {
		    board.dropOnBoard(nextMove, agents.get(i).getCoinNumber());
		    display.displayBoard(board.getBoard());
		    
		    //if the current agent has won the game, print the agent that won and stop the game .
		    if (board.isHasWon()) {
			System.out.println(agents.get(i).getName() + " has WON!");
			continueTheGame = false;
			break;
		    }

		    //if the board is full then the game is a draw, print the 'Draw Game' and stop the game.
		    if (board.IsBoardFull()) {
			System.out.println("Draw Game");
			continueTheGame = false;
			break;
		    }
		} else {
		    // loop to current player once again if a coin could not be placed due to the
		    // lane being full.
		    i--;
		}
	    }
	}
    }

    @Override
    public List<Agent> getAgents() {
	return agents;
    }

    @Override
    public IBoard getBoard() {
	return board;
    }

}
