package bathprj.controller;

/*
NegaMaxMemoryUnit
---------------------------
This class is of a negamax memory unit (transposition) 
*/
public class NegaMaxMemoryUnit {
    private int depth;
    private int currentBestValue;
    private int flag;
    
    public int getDepth() {
	return depth;
    }
    
    public void setDepth(int depth) {
	this.depth = depth;
    }
    public int getCurrentBestValue() {
	return currentBestValue;
    }
    public void setCurrentBestValue(int currentBestValue) {
	this.currentBestValue = currentBestValue;
    }
    public int getFlag() {
	return flag;
    }
    public void setFlag(int flag) {
	this.flag = flag;
    }
}
