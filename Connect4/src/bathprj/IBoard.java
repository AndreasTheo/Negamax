package bathprj;

/*
IBoard
---------------------------
This Interface provides an abstract representation of a connect4 board
*/
public interface IBoard {

    public boolean canDrop(int nextMove);

    public void dropOnBoard(int nextMove, int coinNumber);

    public int[][] getBoard();

    public void resetBoard();

    public boolean isHasWon();

    public int ScoreEntireBoardByCoinNum(int coinNum);

    public int undoMove();

    public int getRows();

    public int getColumns();

    public boolean IsBoardFull();
}
