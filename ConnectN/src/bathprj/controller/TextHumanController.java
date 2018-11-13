package bathprj.controller;

import bathprj.IConnectN;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
TextHumanController
---------------------------
 This class is a text controller class enabling a user to make a move using text input
*/
public class TextHumanController implements IAgentController {

    private Agent agent;

    /*
    Constructor
    */
    public TextHumanController(Agent agent) {
	this.agent = agent;
    }

    /*
    getMove
    ---------------------------
	This method gets the move of the current agent via a text buffer input
    */
    @Override
    public int getMove(IConnectN connectN) {
	try {
	    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	    //print agent's name
	    System.out.print(agent.getName() + ": ");
	    //get user input
	    String string = input.readLine();
	    
	    int action = -1;
	    if (string.length() > 0) {
		//convert char to number and minus the number by 1 given the action 1 will be used to address 
		//a board position of 0.
		action = string.charAt(0) - '0' - 1;
	    }
	    if (action >= 0 && action < connectN.getBoard().getColumns()) {
		return action;
	    } else {
		System.out.println("invalid Input, try again");
		return getMove(connectN);
	    }
	} catch (IOException e) {
	    System.err.println("error getting " + agent.getName() + "'s move");
	    System.err.println(e);
	}

	return -1;
    }

    public void parseInput() {

    }

}
