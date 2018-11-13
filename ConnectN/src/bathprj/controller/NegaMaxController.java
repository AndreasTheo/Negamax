package bathprj.controller;

import bathprj.IBoard;
import bathprj.IConnectN;
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
    private HashMap<Integer, NegaMaxMemoryUnit> hashMap;

    /*
    Constructor
    */
    public NegaMaxController(Agent agent) {
	this.hashMap = new HashMap<Integer, NegaMaxMemoryUnit>();
	this.agent = agent;
	this.initialDepth = 5;
    }

    /*
    Get Move
    ---------------------------
 	This method gets the move of the current agent via a negamax algorithm
    */
    @Override
    public int getMove(IConnectN connectN) {
	NegaMax(connectN);
	return bestMove;
    }

    /*
    NegaMax
    ---------------------------
	This method is an entry method to a specific negamax algorithm
    */
    public void NegaMax(IConnectN connectN) {
	connectN.getAgents();
	ArrayList<Integer> playerIDs = new ArrayList<Integer>();
	//add the game agents coin numbers/types to a list of playerID's
	for (int i = 0; i < connectN.getAgents().size(); i++) {
	    playerIDs.add(connectN.getAgents().get(i).getCoinNumber());
	}

	//cycle the list until the first element of the list has the same coin number as this agent.
	//this is used to make the first player to be considered during the negaMax algorithm, the agent who owns this controller.
	for (int i = 0; i < connectN.getAgents().size(); i++) {
	    if (agent.getCoinNumber() == playerIDs.get(0)) {
		break;
	    } else {
		cycleIList(playerIDs);
	    }
	}

	if (playerIDs.size() == 2) {
	    twoPlayerNegaMax(connectN.getBoard(), initialDepth, -99999, 99999, playerIDs.get(0), playerIDs.get(1));
	} else {
	    nPlayerNegaMax(connectN.getBoard(), initialDepth, playerIDs);
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
	int boardKey = board.getBoardConfigurationKey();

	//if are in a known board configuration and the depth is equal or greater than what we have already seen before,
	//use the values saved last time in a transposition table.
	if (hashMap.containsKey(boardKey)) {
	    NegaMaxMemoryUnit tranposition = hashMap.get(boardKey);
	    if (hashMap.get(boardKey).getDepth() >= depth) {
		switch (tranposition.getFlag()) {
		case 0: //value saved was an upperbound
		    beta = Math.min(beta, tranposition.getCurrentBestValue());
		    break;
		case 1: //value saved was a lowerbound
		    alpha = Math.max(alpha, tranposition.getCurrentBestValue());
		    break;
		case 2: //value saved was exact
		    return tranposition.getCurrentBestValue();
		}
		if (alpha > beta) {
		    return tranposition.getCurrentBestValue();
		}
	    }
	}
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
	
	//add the values found at the current board configuration to the transposition table
	NegaMaxMemoryUnit memoryUnit = new NegaMaxMemoryUnit();
	memoryUnit.setCurrentBestValue(currentBestValue);
	if (currentBestValue <= startingAlpha) {
	    memoryUnit.setFlag(0); //value is an upperbound
	} else if (currentBestValue >= beta) {
	    memoryUnit.setFlag(1); //value is a lowerbound
	} else {
	    memoryUnit.setFlag(2); //value is a exact
	}
	memoryUnit.setDepth(depth);
	hashMap.put(boardKey, memoryUnit);

	return currentBestValue;
    }

    /*
    nPlayerNegaMax
    ---------------------------
	this method is for an N player negamax algorithm
    */
    public int nPlayerNegaMax(IBoard board, int depth, ArrayList<Integer> playerIDs) {
	int currentBestValue = Integer.MIN_VALUE;
	int boardKey = board.getBoardConfigurationKey();

	//if are in a known board configuration and the depth is equal or greater than what we have already seen before,
	//use the values saved last time in a transposition table.
	if (hashMap.containsKey(boardKey)) {
	    if (hashMap.get(boardKey).getDepth() >= depth) {
		return hashMap.get(boardKey).getCurrentBestValue();
	    }
	}

	//Receive the current score from the perspective of the current player's (playerIDs.get(0)) 
	//coin number/type taking into account all other opponents.
	int score = board.ScoreEntireBoardByCoinNum(playerIDs.get(0));
	for (int p = 1; p < playerIDs.size(); p++) {
	    score -= board.ScoreEntireBoardByCoinNum(playerIDs.get(p));
	}

	//return the score if the game has been won (and we are not in the initial depth) or we have reached the terminal depth
	if (depth < 1 || (board.isHasWon() && depth != initialDepth)) {
	    return score;
	}
	//cycle a new list with the same values of the list playerIDs once to make the next agent's coin number in the playable
	//sequence the first element of the list
	ArrayList<Integer> newList = new ArrayList<Integer>(playerIDs);
	cycleIList(newList);

	//consider all available move positions
	for (int i = 0; i < board.getColumns(); i++) {
	    if (board.canDrop(i)) {
		board.dropOnBoard(i, playerIDs.get(0));
		int moveScore = 0;
		boolean didIWin = board.isHasWon();
		//get the negative of the maximum score the opponent could find
		moveScore = -nPlayerNegaMax(board, depth - 1, newList);
		//to avoid having the current player (playerIDs.get(0)) make a move which would guarante 
		//the next player gets a large negative score (when the player after the next will be guaranteed  a win
		// make the score a negative if it's over 9000 and the current player has not won.
		if (moveScore > 9000 && !didIWin) {
		    moveScore = -moveScore;
		}
		board.undoMove();

		if (moveScore > currentBestValue || moveScore == currentBestValue) {
		    currentBestValue = moveScore;
		    if (depth == initialDepth) {

			bestMove = i;
		    }
		}
	    }
	}
	//add the values found at the current board configuration to the transposition table
	NegaMaxMemoryUnit memoryUnit = new NegaMaxMemoryUnit();
	memoryUnit.setCurrentBestValue(currentBestValue);
	memoryUnit.setDepth(depth);
	hashMap.put(boardKey, memoryUnit);

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
