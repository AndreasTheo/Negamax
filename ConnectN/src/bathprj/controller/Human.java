package bathprj.controller;

/*
Human
---------------------------
This class for a human player with an agent controller currently set to a text based controller
*/
public class Human extends Agent {

    public Human(String name, int coinNumber) {
	super(name, coinNumber);
	// default controller for the player
	setAgentController(new TextHumanController(this));
    }
}
