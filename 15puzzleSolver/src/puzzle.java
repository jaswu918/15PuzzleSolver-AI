/*This is almost the same as the puzzle.java file I had for homework2. The only difference is added methods 
and variables to help with A* search. This is shown between a bunch of ////// marks below*/
import java.util.*;

public class puzzle implements Comparable<puzzle>{
	int random1, random2; //these first int declarations are used to determine how to create the puzzle.
	int[] options=new int[3];
	int[][] solved = { { 1, 2, 3, 4 }, 
						{ 5, 6, 7, 8 }, 
						{ 9, 10, 11, 12 },
						{ 13, 14, 15, 0 } }; // Correct solution
	int[][] newPuzzle = new int[4][4]; // this will be the 2d array being built
	int[][] correct = { { 1, 2, 3, 4 }, 
			{ 5, 6, 7, 8 }, 
			{ 9, 10, 11, 12 },
			{ 13, 14, 15, 0 } };
	puzzle parent;
	puzzle child;
	int blankR, blankC; //keep track of the blank tile location on 2d array
	int depth = 0;  //depth is used for IDS
	String solution = ""; //Keep track of where to swap to match the correct solution
	////////////////////////////////////////////////////////////////////////////////////////
	int numOfMisplaced; //used for a* search --h(n)
	int function; //f(n)=g(n)+h(n)
	///////////////////////////////////////////////////////////////////////////////////////

	public puzzle(puzzle child) { // Constructor that defines each puzzle node
		expand(child);
		parent = child.parent;
		this.child = child.child;
		blankR = child.blankR;
		blankC = child.blankC;
		solution = child.solution;
		depth = child.depth;
	}

	public puzzle(int[][] array) { // Another constructor to take in an array.
		newPuzzle = array;
		blankTile(newPuzzle);
	}

	public puzzle() { // constructor for accessing methods
	}

	public void expand(puzzle child) { // this method takes in an 2d array puzzle object on transfers to newPuzzle[][]
		blankTile(newPuzzle);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				newPuzzle[i][j] = child.newPuzzle[i][j];
			}
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Everything between the bars for puzzle file are different from homework 2's puzzle file
	
	public int misplaceCheck() { //The number of wrong placed tiles in comparison to the solved 15 puzzle state will determine
								//the estimate cost of the shortest path from current node to the goal.--h(n)
		numOfMisplaced=0;
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(newPuzzle[i][j]!=correct[i][j] && newPuzzle[i][j]!=0) 
					numOfMisplaced++;  //traversing the 2d array, if any tiles are in the wrong place, increment numOfMisplaced.
			}
		}
		return numOfMisplaced;
	}
	public int compareTo(puzzle other) {
		homework3 test=new homework3();  //need to access the g(n) in the homework3 class. This is the path cost
		function=test.pathCost + numOfMisplaced;  //cost function = path cost + cost of shortest path node
		if(this.equals(other))  //these if else statements determine the priority of the f(n) cost function. The priority is the smallest cost.
			return 0;
		else if(getFunction()>other.getFunction())
			return 1;
		else
		return -1;
	}
	public int getFunction() { //returns the cost essentially.f(n)
		return function;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int random() { // random returns an int from 0-3, each representing a direction. My thinking is that starting from 
		                    //the solved state, if x amount of steps from the blank tile are moved, then the puzzle will be 
		                    //solvable. This is how I created a random solvable puzzle.
		Random rand = new Random();
		random1= rand.nextInt(4); 
		return random1;
	}
	
	public int random2(int num) { //this is a helped method to random(). While 0=North, 1=East, 2=South, 3=West, we don't want 2 consecutive
								  //random numbers to be (0-2) or (1,3) so that it cancels out the swapping from the blank tile starting point.
		if(num==0) {
			options[0]=0;
			options[1]=1;
			options[2]=3;
			random2=new Random().nextInt(3);
		}else if(num==1) {
			options[0]=0;
			options[1]=1;
			options[2]=2;
			random2=new Random().nextInt(3);
		}else if(num==2) {
			options[0]=1;
			options[1]=2;
			options[2]=3;
			random2=new Random().nextInt(3);
		}else if(num==3) {
			options[0]=0;
			options[1]=2;
			options[2]=3;
			random2=new Random().nextInt(3);
		}
		return options[random2];
	}

	public boolean boundaries(int num) { // 0=North, 1=East, 2=South, 3=West. Can't move positions out of array limits
		if (num == 0 && blankR != 0) {
			return true;
		} else if (num == 1 && blankC != 3) {
			return true;
		} else if (num == 2 && blankR != 3) {
			return true;
		} else if (num == 3 && blankC != 0) {
			return true;
		} else {
			return false;
		}
	}

	public void solution(int i) { // Keeps track of where the blank tile moves.
		if (i == 0)
			solution += "N ";
		else if (i == 1)
			solution += "E ";
		else if (i == 2)
			solution += "S ";
		else if (i == 3)
			solution += "W ";
	}

	public void blankTile(int[][] array) { // knowing where the blank tile is is most important for swaps and solved state.
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++)
				if (array[i][j] == 0) {
					blankR = i;
					blankC = j;
				}
		}
	}

	public void swap(int i, int j, int k, int l) { // this method is used for the searching algorithms.
		int temp = newPuzzle[i][j];
		newPuzzle[i][j] = newPuzzle[k][l];
		newPuzzle[k][l] = temp;
		blankR = k;
		blankC = l;
	}

	public void createSwap(int i, int j, int k, int l) { // slightly different from the top method, this is used to create newPuzzle.
		int temp = solved[i][j];
		solved[i][j] = solved[k][l];
		solved[k][l] = temp;
	}

	public void swapping(int num) { // this method is used for searching algorithms and ""depth" is used for iterative deepening search.
		if (num == 0) {
			swap(blankR, blankC, blankR - 1, blankC);
			depth++;
		} else if (num == 1) {
			swap(blankR, blankC, blankR, blankC + 1);
			depth++;
		} else if (num == 2) {
			swap(blankR, blankC, blankR + 1, blankC);
			depth++;
		} else if (num == 3) {
			swap(blankR, blankC, blankR, blankC - 1);
			depth++;
		}
	}

	public void createSwapping(int num) { // this helps generate the solvable puzzle
		if (num == 0 && boundaries(num) == true) {
			createSwap(blankR, blankC, blankR - 1, blankC);
		} else if (num == 1 && boundaries(num) == true) {
			createSwap(blankR, blankC, blankR, blankC + 1);
		} else if (num == 2 && boundaries(num) == true) {
			createSwap(blankR, blankC, blankR + 1, blankC);
		} else if (num == 3 && boundaries(num) == true) {
			createSwap(blankR, blankC, blankR, blankC - 1);
		} else {
			createSwapping(random2(random())); /* recursively calling itself to prevent empty space to be in the same place after a 
										  createPuzzle method call. Without this, blank tile sometimes does not move on a swap.*/
		}
	}

	public boolean solved() { // this checks if all the elements in two 2d arrays are the same in the same order.
		boolean same = false;
		if (Arrays.deepEquals(newPuzzle, solved)) {
			same = true;
		}
		return same;
	}

	public void printArray(int[][] array) { // this prints the 2d array
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(array[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public String toString() { // when the PrintWriter looks for a string to print to text edit, it will print the appropriate 2d array.
		String array = Arrays.deepToString(newPuzzle).replace("],", "] \n");
		return array;
	}

	public puzzle puzInitializer() { // this creates a new puzzle object and assigns it a new puzzle.
		puzzle puzzle = new puzzle(newPuzzle);
		return puzzle;
	}

	public void createPuzzle(int num) { // this takes some of the previous methods, puts them together to make a
										// solvable puzzle with user determined number of random swaps.
		for (int i = 0; i < num; i++) {
			blankTile(solved);
			createSwapping(random2(random()));
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				newPuzzle[i][j] = solved[i][j];
			}
		}
		System.out.println("Generated Puzzle");
		printArray(newPuzzle);
	}
}
