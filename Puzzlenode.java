/*
 * Puzzlenode class tracks the parent node, level cost, heuristic cost 
 * and evluation cost of the search node
 */
public class Puzzlenode 
{

	private Puzzlestate presentnode; // current node
	private Puzzlenode parentnode; // parent current node
	private int levelcost; // cost to get to this state
	private int heuristiccost; // heuristic cost
	private int Evalfcost; // Evaluation cost f(n) 

	public Puzzlenode(Puzzlestate state)
	{
		presentnode = state;
		parentnode = null;
		levelcost = 0;
		heuristiccost = 0;
		Evalfcost = 0;
	}

	
	public Puzzlenode(Puzzlenode parent, Puzzlestate present, int gcost, int hcost)
	{
		parentnode = parent;
		presentnode = present;
		levelcost = gcost;
		heuristiccost = hcost;
		Evalfcost = levelcost + heuristiccost;
	}

	public Puzzlestate getpresentnode()
	{
		return presentnode;
	}

	public Puzzlenode getparentnode()
	{
		return parentnode;
	}

	public int getheuristiccost()
	{
		return heuristiccost;
	}


	public int getlevelCost()
	{
		return levelcost;
	}


	public int getEvalfcost()
	{
		return Evalfcost;
	}
	
	
}