//Main method that calls BFS, DFS, IDS.
import java.io.*;

public class textFile {
	public static void main(String[] args)throws IOException {
		File file=new File("result.txt"); //These first 4 lines are used to create text file and print the outputs.
		FileWriter fw=new FileWriter(file);
		PrintWriter pw=new PrintWriter(fw);
		search solve = new search(); 
		//BFS
		puzzle puzzle1 = new puzzle(); 
		puzzle1.createPuzzle(15); //createPuzzle(num) is used to create a puzzle with num swaps
		puzzle breadth = solve.BFS(puzzle1.puzInitializer());
		pw.println("Breadth First Search: ");
		pw.println(puzzle1);
		pw.println(breadth.solution);
		//DFS
		puzzle puzzle2 = new puzzle();
		puzzle2.createPuzzle(1);
		//pw.println("My solution for DFS is one swap because I keep running into the java heap space error on moves 2 or more. \nMy code might be faulty somewhere and I'm struggling to find where.");
		puzzle depth = solve.DFS(puzzle2.puzInitializer());
		pw.println("Depth First Search: ");
		pw.println(puzzle2);
		pw.println(depth.solution);
		//IDS
		puzzle puzzle3 = new puzzle();
		puzzle3.createPuzzle(15);
		puzzle iterative = solve.IDS(puzzle3.puzInitializer());
		pw.println("Iterative Deepening Search: ");
		pw.println(puzzle3);
		pw.println(iterative.solution);
		pw.close();
		System.out.println("Finished!"); //When "Finished!" is displayed on the console, it means that all 3 searches are finished and printed on a text file.
	}
}
