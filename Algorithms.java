import java.util.*;

public class Algorithms {

	public static int NODECOUNT = 1;
	public static int CHECK = 0;
	public static String alg;
	public static Boolean with_time = false;
	public static Boolean with_open_list = false;

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
		start.unMark();
		NODECOUNT=1;
		Stack<Node> openList = new Stack<Node>();
		Hashtable<Node,Node> H = new Hashtable<Node,Node>();
		openList.add(start);
		H.put(start,start);
		String result = "no path\nNum:";
		/*		UpperBound		*/
		int factorail = 1;
		for(int i = 1; i <= Node.GOAL.length*Node.GOAL[0].length; i++)
			factorail *= i;
		int t = Math.min(factorail, Integer.MAX_VALUE);
		while(!openList.isEmpty()) {
			if(with_open_list)
				System.out.println(Arrays.deepToString(openList.toArray()));
			Node n = openList.pop();
			if(n.isOut)
				H.remove(n);
			else {
				n.markOut();
				openList.add(n);
				ArrayList<Node> N = new ArrayList<Node>();
				for(Node temp : n.operators()) 
					N.add(temp);
				N.sort((a,b) -> a.f() - b.f());
				for(int i = 0; i < N.size(); i++) {
					Node g = N.get(i);
					NODECOUNT++;
					if(g.f() >= t) {	//if f(g)>=t
						int temp = t;
						N.removeIf((Node a) -> a.f() >= temp);
					}
					else //f(g)<t
						if(H.contains(g) && H.get(g).isOut) {	//if H contains g'=g and g' isMarked
							N.remove(g);
						}
						else {	
							if(H.contains(g) && !H.get(g).isOut) { //if H contains g'=g and g' isnt Marked
								if(H.get(g).f() <= g.f()) {
									N.remove(g);
								}
								else {
									openList.remove(g);
									H.remove(g);
								}
							}
							else {	// if H doesnt contains g && f(g)
								if(g.isGoal()) {
									t = g.f();
									result = g.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + g.getCost();
									int temp = t;
									N.removeIf((Node a) -> a.f() >= temp);
								}
							}
						}
				}
				for(int i = 0; i < N.size(); i++) {
					openList.add(N.get(N.size()-1-i));
					H.put(N.get(N.size()-1-i),N.get(N.size()-1-i));
				}
			}
		}
		if(result.contains("no path"))
			return "no path\nNum: " + NODECOUNT;
		return result;
	}
	/**************** IDA* ****************/
	public static String IDA(Node start){
		start.unMark();
		NODECOUNT=1;
		Stack<Node> openList = new Stack<Node>();
		Hashtable<Node, Node> H = new Hashtable<Node, Node>();
		int t = start.h();
		while(t!=Integer.MAX_VALUE) {
			int minF = Integer.MAX_VALUE;
//			start.unMark();
			openList.add(start);
			H.put(start, start);
			while(!openList.isEmpty()) {
				if(with_open_list)
					System.out.println("OpenList::"+Arrays.deepToString(openList.toArray()));
				Node n = openList.pop();
				if(n.isOut()) {
					H.remove(n);
				}
				else {
					n.markOut();
					openList.add(n);
					for(Node g : n.operators()) {
						NODECOUNT++;
						if(g.f() > t) {
							minF = Math.min(minF, g.f());
							continue;
						}
						if(H.contains(g) && H.get(g).isOut()) {
							continue;
						}
						if(H.contains(g) && !H.get(g).isOut()) {
							if(H.get(g).f() > g.f()) {
								openList.remove(H.get(g));
								H.remove(H.get(g));
							}
							else {
								continue;
							}
						}
						if(g.isGoal()) {
							return g.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + g.getCost();
						}
						openList.add(g);
						H.put(g, g);
						}
				}
			}
			t = minF;
		}
		return "no path\nNum: " + NODECOUNT;
	}
	/**************** A* ****************/
	public static String UCS(Node start){
		NODECOUNT=1;
		PriorityQueueNode openList = new PriorityQueueNode();
		Hashtable<Node,Node> openListHash = new Hashtable<>();
		openList.add(start);
		openListHash.put(start,start);
		Hashtable<Node, Node> closeList = new Hashtable<>();
		while(!openList.isEmpty()){
			if(with_open_list)
				System.out.println(Arrays.deepToString(openList.toArray()));
			Node n = openList.poll();
			openListHash.remove(n);
			if (n.isGoal())
				return n.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + n.getCost();
			closeList.put(n,n);
			for(Node x : n.operators()){
				NODECOUNT++;
				if(!openListHash.contains(x) && !closeList.contains(x)){
					openList.add(x);
					openListHash.put(x,x);
				}
				else{
					if(openListHash.contains(x) && openListHash.get(x).f() > x.f()){
						openList.swapForLowerValue(x);
					}
				}
			}
		}
		return "no path\nNum: " + NODECOUNT;
	}

	/**************** DFID ****************/
	public static String DFID(Node start){
		NODECOUNT=1;
		String result;
		for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
			Hashtable<Node,Node> ht = new Hashtable<>();
			result = Limited_DFS(start, depth, ht);
			if(!result.equals("Cutoff"))
				if(result.equals("fail"))
					return "no path\nNum: " + NODECOUNT;
				else
					return result;
		}
		return "no path\nNum: " + NODECOUNT;
	}

	private static String Limited_DFS(Node n, int limit, Hashtable<Node,Node> h){
		if(n.isGoal())
			return n.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + n.getCost();
		if(limit == 0)
			return "Cutoff";
		h.put(n,n);
		boolean isCutoff = false;
		Queue<Node> op = n.operators();
		while(!op.isEmpty()){
			Node g = op.poll();
			NODECOUNT++;
			if(h.contains(g))
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
		h.remove(n);
		if (isCutoff){
			return "Cutoff";
		}
		else{
			return "fail";
		}
	}

	/**************** BFS ****************/
	public static String BFS(Node start){
		NODECOUNT=1;
		Queue<Node> openList = new LinkedList<Node>();
		Hashtable<Node, Node> H = new Hashtable<Node, Node>();
		openList.add(start);
		H.put(start, start);
		Hashtable<Node, Node> closeList = new Hashtable<Node, Node>();
		while(!openList.isEmpty()){
			if(with_open_list)
				System.out.println(Arrays.deepToString(openList.toArray()));
			Node n = openList.poll();
			H.remove(n);
			closeList.put(n,n);
			for(Node g : n.operators()) {
				NODECOUNT++;
				if(!closeList.contains(g) && !H.contains(g)) {
					if(g.isGoal())
						return g.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + g.getCost();
					openList.add(g);
					H.put(g,g);
				}
			}
		}
		return "no path\nNum: " + NODECOUNT;
	}
}

