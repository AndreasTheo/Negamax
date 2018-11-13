package bathprj.controller;

/*
Bot
---------------------------
This class is for a bot player with an AI agent controller
*/
public class Bot extends Agent {

    public Bot(String name, int coinNumber) {
	super(name, coinNumber);
	// default controller for the bot
	setAgentController(new NegaMaxController(this));
    }
}
