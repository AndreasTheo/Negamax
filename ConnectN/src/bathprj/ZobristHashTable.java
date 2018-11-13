package bathprj;

import java.util.HashSet;
import java.util.Random;

/*
ZobristHashTable
---------------------------
This class is for a Zobrist hash table which stores unique piece numbers at each position of a state space
*/
public class ZobristHashTable {

    HashSet<Integer> uniqueRandomNumbers = new HashSet<Integer>();
    Random rand;
    private int[][] hashTable;
    private int stateSpaceSize;
    private int pieces;

    /*
    Constructor
    */
    public ZobristHashTable(int stateSpaceSize, int pieces) {
	this.pieces = stateSpaceSize;
	this.stateSpaceSize = pieces;
	this.hashTable = new int[stateSpaceSize][pieces];
	this.rand = new Random();
    }

    /*
    Create Unique Keys
    ---------------------------
    This method creates a unique random number / key  for each position for each piece within a hashTable.
    */
    public void createUniqueKeys() {
	for (int i = 0; i < hashTable.length; i++) {
	    for (int k = 0; k < hashTable[0].length; k++) {
		hashTable[i][k] = uniqueRandomNumber();
	    }
	}
    }

    /*
    Unique Random Number
    ---------------------------
    This method returns a random number unique to the ZobristHashTable class using a hashmap to guarantee the number 
    has not been returned before.
    */
    public int uniqueRandomNumber() {
	int newRandomNumber = rand.nextInt(Integer.MAX_VALUE);
	//make sure the new random number hasn't been used already by checking if 
	//the number exists in a hashmap we add new random numbers to.
	if (uniqueRandomNumbers.contains(newRandomNumber)) {
	    return uniqueRandomNumber();
	} else {
	    uniqueRandomNumbers.add(newRandomNumber);
	    return newRandomNumber;
	}
    }

    /*
    Print Hash Table
    ---------------------------
    This method prints the Zobrist hash table to the console
    */
    public void printHashTable() {
	for (int i = 0; i < hashTable.length; i++) {
	    for (int k = 0; k < hashTable.length; k++) {
		System.out.print(hashTable[i][k] + " ");
	    }
	    System.out.println();
	}
    }

    public int[][] getHashTable() {
	return hashTable;
    }

}
