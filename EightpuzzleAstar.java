
import java.util.*;
/*
 * This class contains the various methods like A star search on 8-game puzzle board, checking the board 
 * is equal to goal board, priority queue for maintaining open list of nodes
 * 
 */
public class EightpuzzleAstar {

	/**
	 * This method performs the A star search algorithm which takes initial board state as input
	 * 
	 */
	public static void Astarsearch(int[] initialboard)
	{
		Puzzlenode Init = new Puzzlenode(new Puzzlestate(initialboard));

		// linked list to maintain the list of open nodes to be examined
		Queue<Puzzlenode> q = new LinkedList<Puzzlenode>();
		q.add(Init);

		int nodecount  = 1; // counter for number of expanded nodes
		int gennodes   = 1; // counter for number of nodes generated	

		while (!q.isEmpty()) //checks whether the queue is empty or not
		{

			Puzzlenode node =  q.poll();

			if (!node.getpresentnode().isGoal())  //  checks whether the node is goal node
			{

				/*
				 * checksuccessor, Successor array lists to maintain the list of successor states
				 */
				ArrayList<Puzzlestate> checksuccessor = node.getpresentnode().getsuccessor();
				ArrayList<Puzzlenode> Successor = new ArrayList<Puzzlenode>();
				gennodes = gennodes + checksuccessor.size(); // keeps track of number of generated nodes

				for (int i = 0; i < checksuccessor.size(); i++)
				{
					Puzzlenode generatednode;

					int levelcost = node.getlevelCost() +  checksuccessor.get(i).stepcost();
					int heuristiccost = ((Puzzlestate) checksuccessor.get(i)).getmanhattandist();
					/*
					 * generates new puzzlenode by using the constructor 
					 * Puzzlenode(node,parent, levelcost,heuristiccost);
					 */
					generatednode = new Puzzlenode(node,checksuccessor.get(i), levelcost,heuristiccost);

					/*
					 * checks whether the generated node is already in one of the parents node
					 */
					if (!nodeTest(generatednode))
					{
						//Adds the successsor to the array list if the node is not repeated
						Successor.add(generatednode);
					}

				}

				if (Successor.size() == 0)
					continue;

				genMinpriorityQ(Successor,q); // calls the method genMinpriorityQ

				nodecount++;	//Keeps track of number of nodes expanded		

			}

			else

			{
				//	A stack to keep track of path of nodes from root to goal state
				Stack<Puzzlenode> trackstack = new Stack<Puzzlenode>();
				trackstack.push(node);
				node = node.getparentnode();

				while (node.getparentnode() != null)
				{
					trackstack.push(node);
					node = node.getparentnode();
				}
				trackstack.push(node);

				int loopSize = trackstack.size();

				for (int i = 0; i < loopSize; i++)
				{
					node = trackstack.pop();
					node.getpresentnode().printpuzzlestate();
					System.out.println();
					System.out.println();
				}
				System.out.println("The cost to reach goal is: " + node.getlevelCost());

				System.out.println("The number of nodes generated: " + gennodes);
				System.out.println("The number of nodes expanded: " + nodecount);


				System.exit(0);
			}
		}

		// If the input queue which maintains open list is empty
		System.out.println("Error! No solution found!");
		System.exit(0);
	}

	/*
	 * This method will refactor the open linked list to imitate like a priority queue
	 * based on the evaluation cost of the node and add the successors to the list which are 
	 * of minimum evaluation cost
	 */
	private static void genMinpriorityQ(ArrayList<Puzzlenode> minQ,Queue<Puzzlenode> q)
	{
		Puzzlenode minnode = minQ.get(0);

	
		for (int i = 0; i < minQ.size(); i++)
		{
			if (minnode.getEvalfcost() > minQ.get(i).getEvalfcost())
			{
				minnode = minQ.get(i);
			}
		}

		int minval = (int) minnode.getEvalfcost();

		// Adds any nodes that have that same lowest value.
		for (int i = 0; i < minQ.size(); i++)
		{
			if (minQ.get(i).getEvalfcost() == minval)
			{
				q.add(minQ.get(i));
			}
		}
	}

	/*
	 * This method checks whether a node is already evaluated or not, that is, whether
	 * the node is repeated or not	
	 */
	private static boolean nodeTest(Puzzlenode newnode)
	{
		boolean isRepeat = false;
		Puzzlenode tempnode = newnode;

		while (newnode.getparentnode() != null && !isRepeat)
		{
			if (newnode.getparentnode().getpresentnode().equals(tempnode.getpresentnode()))
			{
				isRepeat = true;
			}
			newnode = newnode.getparentnode();
		}

		return isRepeat;
	}

}


