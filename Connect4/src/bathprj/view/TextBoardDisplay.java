package bathprj.view;


/*
TextBoardDisplay
---------------------------
 This class is for a text display that displays a connectN board
*/
public class TextBoardDisplay implements IConnectNDisplay {

    
    /*
    Display Board
    ---------------------------
	This method displays a connect N board to the console
    */
    @Override
    public void displayBoard(int[][] board) {
	
	for (int i = 0; i < board.length; i++) {
	    
	    for (int k = 0; k < board[0].length; k++) {

		System.out.print("| ");
		System.out.print( convertNumToCoinSymbol(board[i][k]) + " ");
		
	    }
	    System.out.println("|");
	}
	System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
    }
    
    /*
    Convert Number To Coin Symbol
    ---------------------------
	This method converts a number to a coin symbol
    */
    public char convertNumToCoinSymbol(int number) {
	if (number == 1) {
	    return 'r';
	} else if (number == 2) {
	    return 'y';
	} else if (number == 3) {
	    return 'b';
	} else {
	    return '_';   
	}
    }

}
