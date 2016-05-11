import java.util.*;

/* Puzzlestate class represents state of the puzzle. The single dimensional array's initial state and goal state
 * passed from input are converted into 2-D arrays (3 by 3) and then manipulations like calculating
 * manhattan distance, performing move operations on board are done on them.
 * Here the '0' in the array represents the hole in the board but it is printed as empty space while 
 * printing interim states and the goal state.
 * 
 */
public class Puzzlestate {

	private int manhattandist = 0; // Manhattan distance
	private int presentmanboard[][] = new int[3][3]  ; // 2-d board current state
	private int goalmanboard[][] = new int[3][3]  ; // 2-d goal state
	private static int[] goalboard;// Goal state
	private int[] presentboard;// Current state
	int holeXpos, holeYpos;// X and Y position of hole in the current board state

	/*
	 * Class Constructor for the board state  
	 */
	public Puzzlestate(int[] board)
	{
		presentboard = board;
		setBoards(presentboard,goalboard);
		findManhattan();
		getholeXY();

	}


	/*
	 * sets the goalboard which is passed from main class
	 */
	public static void setGoalboard(int[] g) 
	{
		goalboard = g;

	}

	/*
	 * This method represents the stepcost that is traversing from one level to next level or state
	 *  which is ideally '1'
	 */

	public int stepcost()
	{
		return 1;
	}



	public int getmanhattandist() {
		return manhattandist;
	}


	public int[] getpresentBoard()
	{
		return presentboard;
	}

	/*
	 * This method converts 1-d arrays of present state and goal state of board to 2-d array for performaing
	 * various operations
	 */
	public void setBoards(int[] presentboard, int[] goalboard ){

		int i=0;
		for(int x=0; x<3; x++){
			for(int y=0; y<3;y++){

				presentmanboard[x][y] = presentboard[y+i];
				goalmanboard[x][y] = goalboard[y+i];
			}
			i = i+3;
		}

	}

	/*
	 * This method finds the manhattan distance of board at current level
	 * 
	 */
	public void findManhattan()
	{


		for(int x=0; x<3;x++){

			for(int y=0; y<3;y++){
				if(presentmanboard[x][y]!=0)
				{
					int temp = presentmanboard[x][y];
					for(int k=0;k<3;k++){
						for (int l=0; l<3;l++){
							if ( temp == goalmanboard[k][l]){
								int xdiff = Math.abs(x-k);
								int ydiff = Math.abs(y-l);
								manhattandist += (xdiff + ydiff);
								break;
							}
						}
					}

				}

			}
		}

	}

	/*
	 * This method makes a copy of board at present level or board passed to this method
	 */
	private int[][] tempboard(int[][] tempstate)
	{
		int[][] tstate = new int[3][3];
		for (int x = 0; x < 3; x++){
			for (int y = 0; y<3 ; y++){
				tstate[x][y] = tempstate[x][y];				
			}

		}
		return tstate;
	}


	/*
	 * This method finds the position of the hole in the puzzle, that is X and Y position of hole
	 */

	private void getholeXY(){
		int x,y = 0;
		for(x=0; x<3;x++){
			for(y=0; y<3;y++){
				if(presentmanboard[x][y]==0){
					holeXpos = x;
					holeYpos = y;

				}
			}
		}
	}

	/*
	 * This method generates the successor for the present state of board. All the successors are maintained in '
	 * the array list "list". The number of successors depends on the goal position. For example when the goal
	 * is in the middle of the board,four successors can be generated and when the hole is at the corner two 
	 * successors can be geneated.
	 */
	public ArrayList<Puzzlestate> getsuccessor()
	{
		ArrayList<Puzzlestate> list = new ArrayList<Puzzlestate>();

		/* If the hole is not in the leftmost column of the board then the 
		 * numbers can be slided into the hole position by moving hole to left
		 */

		if ((holeYpos!=0))
		{
			move(holeXpos,holeYpos-1,holeXpos,holeYpos, list);
		}

		/* If the hole is not in the bottommost row of the board then the 
		 * numbers can be slided into the hole position by moving hole to down
		 */

		if ((holeXpos!=2) )
		{

			move(holeXpos+1,holeYpos,holeXpos, holeYpos, list);
		}

		/* If the hole is not in the topmost row of the board then the 
		 * numbers can be slided into the hole position by moving hole to up
		 */
		if ((holeXpos!=0))
		{

			move(holeXpos-1,holeYpos,holeXpos,holeYpos , list);
		}

		/* If the hole is not in the rightmost column of the board then the 
		 * numbers can be slided into the hole position by moving hole to right
		 */
		if ((holeYpos!=2))
		{

			move(holeXpos,holeYpos+1,holeXpos,holeYpos, list);
		}

		return list;
	}

	/**
	 * This method performs the move or switch operation on the current board
	 * based on the passed indexes x1,x2,y1,y2 and creates new board which is added to successor array list
	 */

	private void move(int x1, int y1, int x2, int y2, ArrayList<Puzzlestate> list)
	{

		int[][] moveboard = tempboard(presentmanboard);
		int temp = moveboard[x1][y1];
		moveboard[x1][y1] = presentmanboard[x2][y2];
		moveboard[x2][y2] = temp;
		int[] newboard = new int[9];
		int i=0;
		for (int x=0 ;x<3; x++){
			for (int y=0; y<3; y++)
			{
				newboard[y+i] = moveboard[x][y];
			}
			i = i+3;

		}

		list.add((new Puzzlestate(newboard)));

	}

	/**
	 * This method checks whether the present state is equal to board state
	 */

	public boolean isGoal()
	{
		if (Arrays.equals(presentboard,goalboard))
		{
			return true;
		}
		return false;
	}



	/*
	 * This method checks whether the present and passed states are equal or not
	 *  
	 */

	public boolean equals(Puzzlestate state)
	{
		if (Arrays.equals(presentboard, ((Puzzlestate) state).getpresentBoard()))
		{
			return true;
		}
		else
			return false;

	}

	/**
	 *This method prints the current state
	 */

	public void printpuzzlestate()
	{

		for(int x=0; x<3; x++){
			for(int y=0; y<3;y++){
				if(presentmanboard[x][y] == 0){
					System.out.print("  ");
				}
				else if(presentmanboard[x][y] != 0){
					System.out.print(" " + presentmanboard[x][y]);
				}
			}
			System.out.println();
		}

	}

}


