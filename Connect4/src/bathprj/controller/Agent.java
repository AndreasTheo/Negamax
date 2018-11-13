package bathprj.controller;

/*
Agent
---------------------------
This abstract class represents an agent which has access to an interface agent controller.
*/
public abstract class Agent {

    private IAgentController agentController;
    private String name;
    private int coinNumber;

    // Default Constructor
    public Agent(String name, int coinNumber) {
	this.name = name;
	this.coinNumber = coinNumber;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getCoinNumber() {
	return coinNumber;
    }

    public void setCoinNumber(int coinNumber) {
	this.coinNumber = coinNumber;
    }

    public IAgentController getAgentController() {
	return agentController;
    }

    public void setAgentController(IAgentController agentController) {
	this.agentController = agentController;
    }

}
