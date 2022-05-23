//Class containing A* search algorithm
import java.util.*;

public class homework3{
	LinkedList<puzzle> informed = new LinkedList<>(); //informed linked list is storing the node with the optimized path
	PriorityQueue<puzzle> shortest=new PriorityQueue<>(); //priority queue used to store expanded nodes, and retrieves the smallest based on f(n)=g(n)+h(n) represented in puzzle class
	int pathCost; // g(n)
	int nodeCount; //used to count the amount of expanded nodes

	// a* Search
	public puzzle aSearch(puzzle initial) {
		pathCost = 0;  //just making sure pathCost and nodeCount instance variables are reset
		nodeCount=0;
		informed.add(initial);  //adding initial node
		if (initial.solved()) {  //early goal test
			return initial;
		}
		while (informed.isEmpty() == false) {  //keep going while list is not empty.
			puzzle parent = new puzzle(informed.poll()); //this will constantly change. "parent" takes the head of puzzle from informed LinkedList which will be the cheapest child node after priority queue poll().
			for (int i = 0; i < 4; i++) {
				puzzle child = new puzzle(parent);
				if (child.boundaries(i) == true) {
					child.swapping(i);
					child.solution(i);
					nodeCount++;   //nodeCount++ is used to see how many nodes generate to compare with BFS
					shortest.add(child);  //priority queue add all the child nodes during expansion. What gets retrieved by methods like poll() or peek() is set by compareTo in puzzle class.
					if (child.solved()) {
						return child;
					} else {
						informed.add(shortest.poll());  //shortest is the priority queue. It return the puzzle object/node with the smallest f(n)=g(n)+h(n).
					}
				}
			}
			pathCost++; //for every expand of the parent node, pathCost(g(n)) gets incremented.
		}
		return new puzzle();
	}
	
	//I included BFS algorithm to test the node difference against A* search
	public puzzle BFS(puzzle initial) {
		nodeCount=0;
		Queue<puzzle> breadth = new LinkedList<>(); //Data structure for BFS
		Queue<puzzle> duplicate = new LinkedList<>();
		breadth.add(initial);
		duplicate.add(breadth.peek()); //adding to duplicates to prevent revisited nodes.
		if (initial.solved()) {
			return initial;
		}
		while (breadth.isEmpty() == false) {
			puzzle parent = new puzzle(breadth.poll());
			for (int i = 0; i < 4; i++) {
				puzzle child = new puzzle(parent);
				if (child.boundaries(i) == true && duplicate.contains(child) == false
						&& breadth.contains(child) == false) {
					child.swapping(i);  //this basically makes the child object to the parent node.
					child.solution(i);  //each traversal has its own solution.
					nodeCount++;
					if (child.solved()) {
						return child;
					} else {
						nodeCount++;
						breadth.add(child); //if possible, add all child nodes onto the queue.
						duplicate.add(child);
					}
				}
			}
		}
		return new puzzle();
	}
	
	public int getNodeCount() { //returns node count.
		return nodeCount;
	}
}
