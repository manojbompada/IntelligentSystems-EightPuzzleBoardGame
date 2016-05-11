import java.util.*;
public class Main {
	/*
	 Give the input array list (for both initial state and goal state) in command line arguments
	 Example: if initial state is 2 8 3 1 6 4 7 0 5 and goal state is 1 2 3 8 0 4 7 6 5 then give input as below
	 2 8 3 1 6 4 7 0 5   1 2 3 8 0 4 7 6 5 
	 
	 '0' represents the empty slot in the board
	 */
	public static void main(String[] args)
	{

		try{
			int[] inputarray = new int[args.length];
			for (int i=0; i<args.length; i++)
			{
				inputarray[i] = Integer.parseInt(args[i]);
			}

			int[] Startstate = Arrays.copyOfRange(inputarray, 0,inputarray.length/2 );
			int[] Goalstate  = Arrays.copyOfRange(inputarray, inputarray.length/2 , inputarray.length);

			Puzzlestate.setGoalboard(Goalstate);
			EightpuzzleAstar.Astarsearch(Startstate);
		}
		catch (NumberFormatException e) {
			System.err.println("skipping wrong format input in the comman line " + args);
		}
	}
}
