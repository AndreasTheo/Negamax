package bathprj;

/*
BoardState
---------------------------
This Class holds variables associated with the current board state
*/
public class BoardState {
    private Position lastMove;
    private boolean hasWonAtLastMove;

    /*
    Constructor
    */
    public BoardState(Position lastMove, boolean hasWonAtLastMove) {
	this.lastMove = lastMove;
	this.hasWonAtLastMove = hasWonAtLastMove;
    }

    public Position getLastMove() {
	return lastMove;
    }

    public boolean isHasWonAtLastMove() {
	return hasWonAtLastMove;
    }
}
