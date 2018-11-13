package bathprj.controller;

import bathprj.IBoard;
import bathprj.IConnect4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
NegaMaxController
---------------------------
This class is a controller class which utilizes a negamax algorithm to make a move
*/
public class NegaMaxController implements IAgentController {

    private Agent agent;
    private int bestMove;
    private int initialDepth;

    /*
    Constructor
    */
    public NegaMaxController(Agent agent) {
	this.agent = agent;
	this.initialDepth = 5;
    }

    /*
    Get Move
    ---------------------------
 	This method gets the move of the current agent via a negamax algorithm
    */
    @Override
    public int getMove(IConnect4 connectN) {
	NegaMax(connectN);
	return bestMove;
    }

    /*
    NegaMax
    ---------------------------
	This method is an entry method to a specific negamax algorithm
    */
    public void NegaMax(IConnect4 connect4) {
	connect4.getAgents();
	ArrayList<Integer> playerIDs = new ArrayList<Integer>();
	//add the game agents coin numbers/types to a list of playerID's
	for (int i = 0; i < connect4.getAgents().size(); i++) {
	    playerIDs.add(connect4.getAgents().get(i).getCoinNumber());
	}

	//cycle the list until the first element of the list has the same coin number as this agent.
	//this is used to make the first player to be considered during the negaMax algorithm, the agent who owns this controller.
	for (int i = 0; i < connect4.getAgents().size(); i++) {
	    if (agent.getCoinNumber() == playerIDs.get(0)) {
		break;
	    } else {
		cycleIList(playerIDs);
	    }
	}

	if (playerIDs.size() == 2) {
	    twoPlayerNegaMax(connect4.getBoard(), initialDepth, -99999, 99999, playerIDs.get(0), playerIDs.get(1));
	} else {
	   // nPlayerNegaMax(connect4.getBoard(), initialDepth, playerIDs);
	}
    }

    /*
    twoPlayerNegaMax
    ---------------------------
	this method is for a two player negamax algorithm
    */
    public int twoPlayerNegaMax(IBoard board, int depth, int alpha, int beta, int myCoin, int opponentCoin) {
	int currentBestValue = Integer.MIN_VALUE;
	int startingAlpha = alpha;

	//receive the current score from the perspective of the coin number/type myCoin.
	int score = board.ScoreEntireBoardByCoinNum(myCoin) - board.ScoreEntireBoardByCoinNum(opponentCoin);

	//return the score if the game has been won (and we are not in the initial depth) or we have reached the terminal depth
	if (depth < 1 || (board.isHasWon() && depth != initialDepth)) {
	    return score;
	}
	//consider all available move positions
	for (int i = 0; i < board.getColumns(); i++) {
	    if (board.canDrop(i)) {
		board.dropOnBoard(i, myCoin);
		//get the negative of the maximum score the opponent could find
		int moveScore = -twoPlayerNegaMax(board, depth - 1, -beta, -alpha, opponentCoin, myCoin);
		board.undoMove();

		if (moveScore > currentBestValue) {
		    currentBestValue = moveScore;
		    if (depth == initialDepth) {
			bestMove = i;
		    }
		}
		alpha = Math.max(alpha, moveScore);
		if (alpha >= beta) {
		    break;
		}
	    }
	}
	
	return currentBestValue;
    }
    
    /*
    cycleIList
    ---------------------------
        This method cycles an a given integer list and returns the list
    */
    public List<Integer> cycleIList(List<Integer> listToCycle) {
	if (listToCycle.size() > 0) {
	    int firstElement = listToCycle.get(0);
	    for (int i = 0; i < listToCycle.size() - 1; i++) {
		listToCycle.set(i, listToCycle.get(i + 1));
	    }
	    listToCycle.set(listToCycle.size() - 1, firstElement);
	}
	return listToCycle;
    }

    public int getInitialDepth() {
	return initialDepth;
    }

    public void setInitialDepth(int initialDepth) {
	this.initialDepth = initialDepth;
    }

}
