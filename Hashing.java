import java.io.*;
import java.util.*;

public class Hashing {
	private FileIO allData; // FileIO for data to add
	private Vector<Vector<Word>> hashingMap; // vector of vectors to create hash map
	private RedBlackTree dataTree; // Red-Black Tree for secondary map
	
	// constructor
	public Hashing(FileIO allData)
	{
		this.allData = allData;
		this.hashingMap = new Vector<Vector<Word>>();
		this.dataTree = new RedBlackTree();
		
		// fill hash map and red-black tree to initialize
		fillHashMap();
		fillDataTree();
	}
	
	// getters and setters
	public FileIO getAllData() { return allData; }
	public void setAllData(FileIO allData) { this.allData = allData; }

	public Vector<Vector<Word>> getHashingMap() {	return hashingMap; }
	public void setHashingMap(Vector<Vector<Word>> hashingMap) { this.hashingMap = hashingMap; }
	
	public RedBlackTree getDataTree() { return dataTree; }
	public void setDataTree(RedBlackTree dataTree) { this.dataTree = dataTree; }

	// helper function: fills the hash map using vector created from FileIO text file parsing
	public void fillHashMap()
	{
		// array of letters to add to hash map
		String letters[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		
		// iterate through alphabet
		for (int i = 0; i < 26; i++)
		{
			// create new vector to add words to for hash map
			Vector<Word> newLine = new Vector<Word>();
			
			// add letter to beginning of line
			newLine.add(new Word(letters[i]));
			
			// check entire array for words that start with letter
			for (int j = 0; j < allData.getWords().size(); j++)
			{
				// if current word starts with current letter, add to newLine vector
				if (allData.getWords().elementAt(j).getValue().startsWith(letters[i]))
				{
					newLine.add(allData.getWords().elementAt(j));
				}
			}
			
			// add finished line to hash map
			hashingMap.add(newLine);
		}
	}
	
	// helper function: fills the data tree using vector created from FileIO text file parsing
	public void fillDataTree()
	{
		// add each array as a node into the data tree
		for (int i = 0; i < hashingMap.size(); i++)
		{
			dataTree.insert(hashingMap.elementAt(i));
		}
	}
	
	// write the text file for equal word counts in the Vector
	public void writeListEqual()
	{
		try
		{
			FileWriter outfile = new FileWriter("resultsListEqual.txt");
			
			// check entire array for words with equal counts
			for (int index = 0; index < allData.getWords().size(); index++)
			{
				Word currWord = allData.getWords().elementAt(index);
				
				// if current word has equal counts, write to text file
				if (currWord.getCountPT() == currWord.getCountYT())
				{
					outfile.write(currWord.getValue() + "		" + currWord.getCountPT() + "\n");
				}
			}
					
			// close file
			outfile.close();
		}
		
		catch (IOException e)
		{
			System.out.println("An error occured.");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}
	}

	// write the text file for different word counts in the Vector
	public void writeListDifference()
	{
		try
		{
			FileWriter outfile = new FileWriter("resultsListDiff.txt");
			
			// check entire array for words with different counts
			for (int index = 0; index < allData.getWords().size(); index++)
			{
				Word currWord = allData.getWords().elementAt(index);
				
				// if current word has unequal counts, write to text file
				// if PT has higher count, calculate difference by subtracting YT from PT
				if (currWord.getCountPT() > currWord.getCountYT())
				{
					// if YT count is zero, output difference with " - ZERO"
					if (currWord.getCountYT() == 0) {outfile.write(currWord.getValue() + "		+" + currWord.getCountPT() + " PT - ZERO\n");}
					
					// otherwise, calculate difference
					else
					{
						int difference = currWord.getCountPT() - currWord.getCountYT();
						outfile.write(currWord.getValue() + "		+" + difference + " PT\n");
					}
				}
				
				// if YT has higher count, calculate difference by subtracting PT from YT
				else if (currWord.getCountPT() < currWord.getCountYT())
				{
					// if PT count is zero, output difference with " - ZERO"
					if (currWord.getCountPT() == 0) {outfile.write(currWord.getValue() + "		+" + currWord.getCountYT() + " YT - ZERO\n");}
					
					// otherwise, calculate difference
					else
					{
						int difference = currWord.getCountYT() - currWord.getCountPT();
						outfile.write(currWord.getValue() + "		+" + difference + " YT\n");
					}
				}
			}
			
			// close file
			outfile.close();
		}
		
		catch (IOException e)
		{
			System.out.println("An error occured.");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}
	}

	// write the text file for equal word counts in the Red-Black Tree
	public void writeEqual()
	{
		try
		{
			// open file
			FileWriter outfile = new FileWriter("resultsEqual.txt");
			
			// use edited version of inorder function from borrowed code
			dataTree.inorder("equal", outfile);
			
			// close file
			outfile.close();
		}
		
		catch (IOException e)
		{
			System.out.println("An error occured.");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}
	}

	// write the text file for different word counts in the Red-Black Tree
	public void writeDifference()
	{
		try
		{
			// open file
			FileWriter outfile = new FileWriter("resultsDiff.txt");
			
			// use edited version of inorder function from borrowed code
			dataTree.inorder("difference", outfile);
			
			// close file
			outfile.close();
		}
		
		catch (IOException e)
		{
			System.out.println("An error occured.");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}
	}
}
