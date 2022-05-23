//Main method that calls A* search
import java.io.*;

public class textFile2 {
	public static void main(String[] args)throws IOException {
		File file=new File("result.txt"); //These first 4 lines are used to create text file and print the outputs.
		FileWriter fw=new FileWriter(file);
		PrintWriter pw=new PrintWriter(fw);
		homework3 solve = new homework3(); 
		//a* Search
		puzzle puzzle1 = new puzzle(); 
		puzzle1.createPuzzle(15); //createPuzzle(num) is used to create a puzzle with num swaps
		puzzle aStar = solve.aSearch(puzzle1.puzInitializer());  //initialize puzzle
		pw.println("A* Search: ");
		pw.println(puzzle1);
		pw.println(aStar.solution);
		pw.println("A* Search Nodes: " + solve.getNodeCount()); //node count for a* seach
		//BFS on the same puzzle for node comparison
		pw.println("BFS: ");  //BFS section to see the difference in nodes compared to a* search
		puzzle BFS = solve.BFS(puzzle1.puzInitializer());
		pw.println(BFS.solution);
		pw.println("BFS Nodes: " + solve.getNodeCount());  //node count for BFS on the same puzzle solved by a* search
		pw.close();
		System.out.println("Finished!"); //When "Finished!" is displayed on the console, it means that the search is finished and printed on a text file.
	}
}