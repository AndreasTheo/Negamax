package bathprj;

/*
BoardState
---------------------------
This Class holds variables associated with the current board state
*/
public class BoardState {
    private Position lastMove;
    private boolean hasWonAtLastMove;
    private int uniqueBoardKeyAtLastMove;

    /*
    Constructor
    */
    public BoardState(Position lastMove, boolean hasWonAtLastMove, int uniqueBoardKeyAtLastMove) {
	this.lastMove = lastMove;
	this.hasWonAtLastMove = hasWonAtLastMove;
	this.uniqueBoardKeyAtLastMove = uniqueBoardKeyAtLastMove;
    }

    public int getUniqueBoardKeyAtLastMove() {
	return uniqueBoardKeyAtLastMove;
    }

    public Position getLastMove() {
	return lastMove;
    }

    public boolean isHasWonAtLastMove() {
	return hasWonAtLastMove;
    }
}
