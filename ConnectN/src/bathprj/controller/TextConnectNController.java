package bathprj.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import bathprj.IConnectN;

/*
TextConnectNController
---------------------------
This class is a text controller for the connectN game
*/
public class TextConnectNController implements IConnectNController {

    /*
    Start Screen
    ---------------------------
	This method prints the starting screen for a Connect N game
    */
    @Override
    public void startScreen() {
	//print starting screen
	System.out.println("Welcome to Connect N");
	System.out.println("There are 3 players red, yellow and blue");
	System.out.println("Player 1 is Red, Player 2 (bot) is Yellow, Player 3 (bot) is blue");
	System.out.println("To play the game type in the number of the column you want to drop you counter in");// done
	System.out.println("A player wins by connecting N counters in a row - vertically, horizontally or diagonally");
	System.out.println("");
    }

    /*
    Options
    ---------------------------
	This method provides the user with the ability to change the options of the connectN game
    */
    @Override
    public void options(IConnectN connectN) {
	setRowToWinCount(connectN);
    }

    /*
    setRowToWinCount
    ---------------------------
	This method provides the user with the ability to change the amount of rows required to win the connectN game
    */
    public void setRowToWinCount(IConnectN connectN) {
	try {
	    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	    System.out.println("Please choose a N input for 2 < N < 7 ");
	    System.out.println("for the number of colours in a row required to win: ");
	    //get user input
	    String string = input.readLine();

	    int action = 0;
	    if (string.length() > 0) {
		//convert char to number 
		action = string.charAt(0) - '0';
	    }
	    if (action > 2 && action < 7) {
		//change the game conditions based on the user input number for N row's to win
		connectN.getBoard().setRowScoreToWin(action);
	    } else {
		System.out.println("invalid Input, try again");
		options(connectN);
	    }
	} catch (IOException e) {
	    System.err.println("error setting in options controller");
	    System.err.println(e);
	}
    }
}
