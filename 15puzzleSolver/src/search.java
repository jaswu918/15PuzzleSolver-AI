//For the most part, I tried to mimic the algorithms on the class lecture notes..
import java.util.*;

public class search {
	//For each search, there will be 2 data structures. One for the implementation, and one to prevent repeating node visits.
	Queue<puzzle> breadth = new LinkedList<>(); //Data structure for BFS
	Queue<puzzle> duplicate = new LinkedList<>();
	Stack<puzzle> stack = new Stack<>(); //Data structure for DFS
	Stack<puzzle> visited = new Stack<>();
	// Stack<puzzle> iterative = new Stack<>();
	// Stack<puzzle> repeat = new Stack<>();
	LinkedList<puzzle> iterative = new LinkedList<>(); //Data structure for IDS
	LinkedList<puzzle> repeat = new LinkedList<>();

	//Breadth First Search
	public puzzle BFS(puzzle initial) {
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
					if (child.solved()) {
						return child;
					} else {
						breadth.add(child); //if possible, add all child nodes onto the queue.
						duplicate.add(child);
					}
				}
			}
		}
		return new puzzle();
	}

	/*
	 * Depth First Search
	 * I'm not entirely confident about this DFS implementation. A lot of times, i
	 * would play around with the ordering of certain push and pop methods
	 * appropriate to DFS but I would always get the heap space error. I keep trying
	 * to visualize this implementation in my head and it seems correct, but never
	 * finished unless its by one move.
	 */
	public puzzle DFS(puzzle initial) {
		stack.push(initial);
		if (initial.solved()) {
			return initial;
		}
		while (stack.isEmpty() == false) {
			visited.push(stack.pop());
			puzzle parent = new puzzle(visited.peek());
			for (int i = 0; i < 4; i++) {
				puzzle child = new puzzle(parent);
				if (child.boundaries(i) == true) {
					child.swapping(i);
					child.solution(i);
					if (child.solved())
						return child;
					else if (visited.contains(child) == false && stack.contains(child) == false)
						stack.push(child);
				}
			}
		}
		return new puzzle();
	}

	/*
	 * Iterative Deepening Search
	 * This implementation works with a linkedList as opposed to a stack for
	 * whatever reason. I tried implement back and for between stacks and linkedList
	 * with methods like pop() or removeLast(). Stacks on the same implementation
	 * seems to take a very long time and I'm not sure it will even finish
	 * implementing. LinkedList works just fine for IDS. I tried the same approach with depth first 
	 * search but it I was still having problems with java heap space.
	 */
	public puzzle IDS(puzzle initial) {
		for (int depth = 0; depth < Double.POSITIVE_INFINITY; depth++) {
			puzzle result = DLS(initial, depth);
			if (result.solved()) //On the class notes, I'm not entirely sure what cutoff meant in the IDS algorithm. 
				return result;
		}
		return new puzzle();
	}

	public puzzle DLS(puzzle initial, int l) {
		iterative.push(initial);
		while (iterative.isEmpty() == false) {
			repeat.push(iterative.peek());
			puzzle parent = new puzzle(iterative.removeLast());
			if (parent.solved()) {
				return parent;
			}
			if (parent.depth > l) {
				return new puzzle();
			} else {
				for (int i = 0; i < 4; i++) {
					puzzle child = new puzzle(parent);
					if (child.boundaries(i) == true && repeat.contains(child) == false) {
						child.swapping(i);
						child.solution(i);
						iterative.push(child);
					}
				}
			}
		}
		return new puzzle();
	}
}
