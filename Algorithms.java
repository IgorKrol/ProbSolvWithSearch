import java.util.*;

public class Algorithms {

	public static int NODECOUNT = 1;
	public static int CHECK = 0;
	public static String alg;
	public static Boolean with_time;
	public static Boolean with_open_list;
	
	public static String run(Node s) {
		switch (alg) {
		case "BFS":
			return BFS(s);
		case "A*":
			return UCS(s);
		case "DFBnB":
			return DFBnB(s);
		case "DFID":
			return DFID(s);
		case "IDA*":
			return IDA(s);
			
		default:
			return null;
		}
	}
	/**************** DFBnB ****************/
	public static String DFBnB(Node start) {
		Stack<Node> L = new Stack<Node>();
		Hashtable<Node,Node> H = new Hashtable<Node,Node>();
		L.add(start);
		H.put(start,start);
		String result = "no path\nNum:" + NODECOUNT;
		int factorail = 1;
		for(int i = 1; i <= Node.GOAL.length*Node.GOAL[0].length; i++)
			factorail *= i;
		int t = Math.min(factorail, Integer.MAX_VALUE);
		while(!L.isEmpty()) {
			Node n = L.pop();
			if(n.isOut)
				H.remove(n);
			else {
				n.markOut();
				L.add(n);
				ArrayList<Node> N = new ArrayList<Node>();
				for(Node temp : n.operators()) 
					N.add(temp);
				N.sort(Node::compareTo);
				for(int i = 0; i < N.size(); i++) {
					Node g = N.get(i);
					NODECOUNT++;
					if(g.getEval() >= t) {	//if f(g)>=t
						int temp = t;
						N.removeIf((Node a) -> a.getEval() >= temp);
					}
					else //f(g)<t
						if(H.contains(g) && H.get(g).isOut) {	//if H contains g'=g and g' isMarked
							N.remove(g);
						}
						else {	
							if(H.contains(g) && !H.get(g).isOut) { //if H contains g'=g and g' isnt Marked
								if(H.get(g).getEval() <= g.getEval()) {
									N.remove(g);
								}
								else {
									L.remove(g);
									H.remove(g);
								}
							}
							else {	// if H doesnt contains g && f(g)
								if(g.isGoal()) {
									t = g.getEval();
									result = g.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + g.getCost();
									int temp = t;
									N.removeIf((Node a) -> a.getEval() >= temp);
								}
							}
						}
				}
				for(int i = 0; i < N.size(); i++) {
					L.add(N.get(N.size()-1-i));
					H.put(N.get(N.size()-1-i),N.get(N.size()-1-i));
				}
			}
		}
		return result;
	}
	/**************** IDA* ****************/
	public static String IDA(Node start){
		Stack<Node> L = new Stack<Node>();
		Hashtable<Node,Node> H = new Hashtable<Node,Node>();
		int t = start.getEval();
		while(t != Integer.MAX_VALUE) {
			int minF = Integer.MAX_VALUE;
			L.add(start);
			H.put(start, start);
			while(!L.isEmpty()) {
				Node n = L.pop();
				if(n.isOut)
					H.remove(n);
				else {
					n.markOut();
					L.add(n);
					Queue<Node> op = n.operators();
					while (!op.isEmpty()) {
						Node g = op.poll();
						NODECOUNT++;
						if(g.getEval() > t) {
							minF = Math.min(minF, g.getEval());
							continue;
						}
						if(H.contains(g) && H.get(g).isOut) continue;
						if(H.contains(g) && !H.get(g).isOut) {
							if(H.get(g).getEval() > g.getEval()) {
								L.remove(g);
								H.remove(g);
							}
							else 	
								continue;
						}
						if(g.isGoal()) 
							return g.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + g.getCost();
						L.add(g);
						H.put(g, g);
					}
					t = minF;
				}
			}
			return "no path\nNum:" + NODECOUNT;
		}
		return "no path\nNum:" + NODECOUNT;
	}
	/**************** A* ****************/
	public static String UCS(Node start){
		PriorityQueueNode queList = new PriorityQueueNode();
		queList.add(start);
		HashSet<Node> closedList = new HashSet<>();
		while(!queList.isEmpty()){
			Node n = queList.poll();
			if(n.isGoal())
				return n.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + n.getCost();
			closedList.add(n);
			Queue<Node> op = n.operators();
			while (!op.isEmpty()){
				Node g = op.poll();
				NODECOUNT++;
				if(!closedList.contains(g) && !queList.contains(g)){
					queList.add(g);
				}
				else{
					if(queList.contains(g)){
						queList.swapForLowerValue(g);
					}
				}
			}
		}
		return "no path\nNum: " + NODECOUNT;
	}

	/**************** DFID ****************/
	public static String DFID(Node start){
		String result;
		for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
			HashSet<int[][]> ht = new HashSet<>();
			result = Limited_DFS(start, depth, ht);
			if(!result.equals("Cutoff"))
				return result;
		}
		return "no path\nNum:" + NODECOUNT;
	}

	private static String Limited_DFS(Node n, int limit, HashSet<int[][]> h){
		if(n.isGoal())
			return n.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + n.getCost();
		if(limit == 0)
			return "Cutoff";
		h.add(n.getBlocks());
		boolean isCutoff = false;
		Queue<Node> op = n.operators();
		while(!op.isEmpty()){
			Node g = op.poll();
			NODECOUNT++;
			if(h.contains(g.getBlocks()))
				continue;
			String result = Limited_DFS(g,limit-1,h);
			if(result.equals("Cutoff"))
				isCutoff=true;
			else{
				if(!result.equals("fail")){
					return result;
				}
			}
		}
		h.remove(n.getBlocks());
		if (isCutoff){
			return "Cutoff";
		}
		else{
			return "fail";
		}
	}

	/**************** BFS ****************/
	public static String BFS(Node start){
		Queue<Node> queList = new LinkedList<Node>();
		queList.add(start);
		HashSet<int[][]> closeList = new HashSet<>();
		while(!queList.isEmpty()){
			Node n = queList.poll();
			closeList.add(n.getBlocks());
			Queue<Node> op = n.operators();
			while(!op.isEmpty()){
				Node g = op.poll();
				NODECOUNT++;
				if(!closeList.contains(g) && !queList.contains(g)){
					if(g.isGoal())
						return g.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + g.getCost();
					queList.add(g);
				}
			}
		}
		return "no path\nNum: " + NODECOUNT;
	}
}
