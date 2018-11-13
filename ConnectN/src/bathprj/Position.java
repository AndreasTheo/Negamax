package bathprj;


/*
Position
---------------------------
This class is of a position object which holds x and y coordinates.
*/
public class Position {

    private int xPos;
    private int yPos;

    /*
    Constructor
    */
    public Position(int xPos, int yPos) {
	this.xPos = xPos;
	this.yPos = yPos;
    }

    public int getxPos() {
	return xPos;
    }

    public void setxPos(int xPos) {
	this.xPos = xPos;
    }

    public int getyPos() {
	return yPos;
    }

    public void setyPos(int yPos) {
	this.yPos = yPos;
    }

    public void setXYPos(int xPos, int yPos) {
	this.xPos = xPos;
	this.yPos = yPos;
    }
}
