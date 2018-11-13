package bathprj;

import java.util.Stack;

/*
Board
---------------------------
Board Class for a Connect4 Game
*/
public class Board implements IBoard {

    private int rows;
    private int columns;
    private int[][] board;
    private boolean hasWon;
    private int rowScoreToWin = 4;
    private Stack<BoardState> boardStateHistory = new Stack<BoardState>();

    /*
    Constructor
    */
    public Board(int rows, int columns, int numOfCoins) {
	this.rows = rows;
	this.columns = columns;
	board = new int[rows][columns];
	hasWon = false;
    }

    /*
	Drop on Board
	---------------------------
	This method allows an agent to drop a coin onto the board at a given position if the position given
	is within range of the column size.
*/
@Override
public void dropOnBoard(int position, int coinNumber) {
	if (position >= 0 && position < columns) {
	    Position currPos = new Position(0, position);

	    for (int i = board.length - 1; i >= 0; i--) {
		if (board[i][currPos.getyPos()] == 0) {
		    board[i][currPos.getyPos()] = coinNumber;
		    currPos.setxPos(i);
		    
		    if (boardStateHistory.size() > 0) {
			boardStateHistory.push(new BoardState(currPos, hasWon));
		    } else {
			boardStateHistory.push(new BoardState(currPos, hasWon));
		    }
		    break;
		}
	    }
	    checkHorizontalWinFromPos(currPos, coinNumber);
	    checkVerticalWinFromPos(currPos, coinNumber);
	    checkDiagonalTLWinFromPos(currPos, coinNumber);
	    checkDiagonalBLWinFromPos(currPos, coinNumber);
	}
}

    /*
	Can Drop
	---------------------------
	This method checks if an agent is able to drop a coin at the current position.
     */
    @Override
    public boolean canDrop(int position) {
	if (position >= 0 && position < columns) {
	    if (board[0][position] == 0) {
		return true;
	    } else {
		return false;
	    }
	}
	return false;
    }

    /*
  	Is Board Full
  	---------------------------
  	This method checks if the board is full by using the size of the history
     */
    @Override
    public boolean IsBoardFull() {
	if (boardStateHistory.size() == rows * columns) {
	    return true;
	} else {
	    return false;
	}
    }

    /*
  	Undo Move
  	---------------------------
  	This method undos the last move made by removing the last element from the history stack and setting the 
  	board position of the last move back to 0 (empty)
     */
    @Override
    public int undoMove() {
	if (boardStateHistory.size() > 0) {
	    Position lastMove = boardStateHistory.peek().getLastMove();
	    board[lastMove.getxPos()][lastMove.getyPos()] = 0;
	    boardStateHistory.pop();
	    if (boardStateHistory.size() > 0) {
		hasWon = boardStateHistory.peek().isHasWonAtLastMove();
	    }
	}
	return 0;
    }

    /*
  	Check Horizontal Win From Position
  	---------------------------
  	This method uses the current position of the coin and check if the surrounding horizontally aligned coins
  	 are of the same number/type.
     */
    public void checkHorizontalWinFromPos(Position pos, int coinNumber) {
	int counter = 0;
	for (int k = 0; k < board[0].length; k++) {
	    if (board[pos.getxPos()][k] == coinNumber) {
		counter++;
		if (counter == rowScoreToWin) {
		    hasWon = true;
		    break;
		}
	    } else {
		counter = 0;
	    }
	}
    }

    /*
   	Check Vertical Win From Position
   	---------------------------
   	This method uses the current position of the coin and check if the surrounding vertically aligned coins
   	 are of the same number/type.
      */
    public void checkVerticalWinFromPos(Position pos, int coinNum) {
	int counter = 0;
	for (int i = 0; i < board.length; i++) {
	    if (board[i][pos.getyPos()] == coinNum) {
		counter++;
		if (counter == rowScoreToWin) {
		    hasWon = true;
		    break;
		}
	    } else {
		counter = 0;
	    }
	}
    }

    /*
	Check Diagonal Top Left Win From Position
	---------------------------
	This method uses the current position of the coin and check if the surrounding 
	diagonally (from top left to bottom right) aligned coins are of the same number/type.
  */
    public void checkDiagonalTLWinFromPos(Position pos, int coinNum) {
	Position startPos = getStartOfDiagonalTL(pos);

	int counter = 0;
	for (int i = startPos.getxPos(), k = startPos.getyPos(); i < board.length && k < board[0].length; i++, k++) {
	    if (board[i][k] == coinNum) {
		counter++;
		if (counter == rowScoreToWin) {
		    hasWon = true;
		    break;
		}
	    } else {
		counter = 0;
	    }
	}
    }

    /*
 	Check Diagonal Bottom Left Win From Position
 	---------------------------
 	This method uses the current position of the coin and check if the surrounding 
 	diagonally (from bottom left to top right) aligned coins are of the same number/type.
   */
    public void checkDiagonalBLWinFromPos(Position pos, int coinNum) {
	Position startPos = getStartOfDiagonalBL(pos);
	int counter = 0;
	for (int i = startPos.getxPos(), k = startPos.getyPos(); i > 0 && k < board[0].length; i--, k++) {
	    if (board[i][k] == coinNum) {
		counter++;
		if (counter == rowScoreToWin) {
		    hasWon = true;
		    break;
		}
	    } else {
		counter = 0;
	    }
	}
    }

    /*
 	Get Start Of Diagonal Top Left
 	---------------------------
 	This method takes the current coin position and finds the point it would reach if it kept moving left then up.
 	The resulting position is used for checking diagonal alignments starting from a top left position.
   */
    public Position getStartOfDiagonalTL(Position pos) {
	Position startPos = new Position(0, 0);

	if (pos.getxPos() > pos.getyPos()) {
	    startPos.setXYPos(pos.getxPos() - pos.getyPos(), 0);

	} else if (pos.getxPos() < pos.getyPos()) {
	    startPos.setXYPos(0, pos.getyPos() - pos.getxPos());

	} else {
	    startPos.setXYPos(0, 0);
	}
	return startPos;
    }

    /*
 	Get Start Of Diagonal Bottom Left
 	---------------------------
 	This method takes the current coin position and finds the point it would reach if it kept moving left then down.
 	The resulting position is used for checking diagonal alignments starting from a bottom left position.
   */
    public Position getStartOfDiagonalBL(Position pos) {
	Position startPos = new Position(pos.getxPos() + pos.getyPos(), 0);
	int lastBoardXPos = board.length - 1;

	if (startPos.getxPos() > lastBoardXPos) {
	    startPos.setXYPos(lastBoardXPos, startPos.getxPos() - lastBoardXPos);
	}
	return startPos;
    }

    /*
   	Score Entire Board By Coin Number
   	---------------------------
   	This method takes in the coin number/type and returns a score related to that coin number/type
     */
    @Override
    public int ScoreEntireBoardByCoinNum(int coinNum) {
	int score = 0;
	score = scoreHorizontals(coinNum) + scoreVerticals(coinNum) + scoreDiagonalBL(coinNum)
		  + scoreDiagonalTL(coinNum);

	//if winning/losing condition has been reached return winning/losing score.
	if (score > 9999) {
	    return 9999;
	} else if (score < -9999) {
	    return -9999;
	}
	return score;
    }

    /*
	Score Horizontals
	---------------------------
	This method score the horizontal alignments given a coin number/type
 */
    public int scoreHorizontals(int coinNum) {
	int score = 0;
	for (int i = 0; i < board.length; i++) {
	    for (int j = 0; j < board[i].length - (rowScoreToWin - 1); j++) {
		score += ScoreCurrentPosition(new Position(i, j), coinNum, 0, 1);
	    }
	}
	return score;
    }

    
    /*
	Score Verticals
	---------------------------
	This method score the verticals alignments given a coin number/type
 */
    public int scoreVerticals(int coinNum) {
	int score = 0;
	for (int i = 0; i < board.length - (rowScoreToWin - 1); i++) {
	    for (int j = 0; j < board[i].length; j++) {
		score += ScoreCurrentPosition(new Position(i, j), coinNum, 1, 0);
	    }
	}

	return score;
    }

    /*
  	Score Diagonal Bottom Left
  	---------------------------
  	This method scores the diagonal alignments (from bottom left to top right) given a coin number/type
   */
    public int scoreDiagonalBL(int coinNum) {
	int score = 0;
	for (int i = 0; i < board.length - (rowScoreToWin - 1); i++) {
	    for (int j = 0; j < board[i].length - (rowScoreToWin - 1); j++) {
		score += ScoreCurrentPosition(new Position(i, j), coinNum, 1, 1);
	    }
	}
	return score;
    }
    /*
	Score Diagonal Top Left
	---------------------------
	This method scores the diagonal alignments (from top left to bottom right) given a coin number/type
*/
    public int scoreDiagonalTL(int coinNum) {
	int score = 0;
	for (int i = board.length - 1; i > (rowScoreToWin - 1); i--) {
	    for (int j = 0; j < board[i].length - (rowScoreToWin - 1); j++) {
		score += ScoreCurrentPosition(new Position(i, j), coinNum, -1, 1);
	    }
	}
	return score;
    }

    /*
	Score Current Position
	---------------------------
	This method moves around the board iteratively in a direction y and direction x for (rowScoreToWin) moves
	then returns the score accumulated in the perspective of a agent given by the coin number/type coinNum.
*/
    public int ScoreCurrentPosition(Position pos, int coinNum, int increaseInX, int increaseInY) {
	int x = pos.getxPos();
	int y = pos.getyPos();
	int deltaY = 0;
	int deltaX = 0;
	int score = 0;

	while (Math.abs(deltaY) < rowScoreToWin && Math.abs(deltaX) < rowScoreToWin) {
	    if (board[x + deltaX][y + deltaY] == coinNum) {
		score += 1;
	    }
	    deltaY += increaseInY;
	    deltaX += increaseInX;
	}
	if (score == rowScoreToWin) {
	    return 9999;
	}
	return score;
    }

    /*
  	Reset Board
  	---------------------------
  	This method resets the 2d board array and clears the board stack history
  */
    @Override
    public void resetBoard() {
	for (int i = 0; i < board.length; i++) {
	    for (int k = 0; k < board[i].length; k++) {
		board[i][k] = 0;
	    }
	}
	boardStateHistory.clear();
    }

    @Override
    public int[][] getBoard() {
	return board;
    }

    @Override
    public boolean isHasWon() {
	return hasWon;
    }

    @Override
    public int getRows() {
	return rows;
    }

    @Override
    public int getColumns() {
	return columns;
    }

}