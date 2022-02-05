import java.io.*;
import java.util.*;

public class FileIO {
	private Vector<Word> words; // list of Words created from words found
	
	// constructor
	public FileIO()	{ words = new Vector<Word>(); }

	// getter and setter
	public Vector<Word> getWords() { return words; }
	public void setWords(Vector<Word> words) { this.words = words;	}

	// helper function: check whether an input is just a number for parsing text files
	public boolean isNumeric(String input)
	{
		// if try block throws an error then input is not just a number
		try
		{
	        int testInput = Integer.parseInt(input);
	        return true;
	    }
		
		// want to get errors thrown and continue after so leave blank
		catch (NumberFormatException e) {}
		
		// if reaches this far, must be at least partially string
	    return false;
	}

	// check if the text file can be opened
	public boolean openTextFile(String filename)
	{
		// create new scanner and try it
		Scanner infile = null;
		
		try { infile = new Scanner(new FileReader(filename)); }
		
		// if error thrown, text file is missing
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}
		
		// otherwise, close file and return true
		infile.close();
		return true;
	}
	
	// open and save data from text file
	public void readTextFile(String filename)
	{
		// use openTextFile to check if text file is present
		if (openTextFile(filename))
		{
			// if file is present, read in data using a try/catch block
			try
			{
				// scanner for reading file
				Scanner infile = new Scanner(new FileReader(filename));
				
				// while not eof, keep getting lines
				while(infile.hasNextLine())
				{
					// grab next line
					String line = infile.nextLine();
										
					// uses helper function and makes sure line is actual data since Panopto has timestamps included
					if (!isNumeric(line) && !line.contains("-->"))
					{
						// tokenizer for grabbing words
						StringTokenizer tokenizer = new StringTokenizer(line);

						// while not end of line, keep getting words
						while(tokenizer.hasMoreTokens())
						{
							boolean wordExists = false; // check for if a word already exists in the word vector
							Word newToken = new Word(tokenizer.nextToken()); // placeholder for word grabbed
							
							newToken.setValue(newToken.getValue().replaceAll("[^a-zA-Z'-]+", "")); // remove punctuation
							
							if (newToken.getValue() == " " || newToken.getValue() == "") { continue; } // if word is blank after removing punctuation, end loop
							
							// check vector to see whether word exists or not
							for (int index = 0; index < words.size(); index++)
							{
								Word currWord = words.elementAt(index); // use variable to shorten if statement code

								// if current word matches an existing word, increase the word's count
								if (newToken.getValue().compareToIgnoreCase(currWord.getValue()) == 0)
								{
									wordExists = true; // since found, word must exist in vector
									if (filename == "PT1.txt") { currWord.setCountPT(currWord.getCountPT() + 1); } // if reading PT file, add to PT count
									else if (filename == "YT1.txt") { currWord.setCountYT(currWord.getCountYT() + 1); } // if reading YT file, add to YT count
								}
							}
							
							// add new Word to Vector only if no match found
							if(wordExists == false)
							{
								this.words.addElement(newToken); // add to vector
								if (filename == "PT1.txt") { this.words.lastElement().setCountPT(1); } // if reading PT file, set PT count to 1
								else if (filename == "YT1.txt") { this.words.lastElement().setCountYT(1); } // if reading YT file, set YT count to 1
							}
						}
					}
				}
				
				// sort vector when done inserting
				Collections.sort(this.words);
				
				// close file
				infile.close();
			}
				
			// error catch in case something goes wrong
			catch (IOException e)
			{
				System.out.println("An error occured.");
				e.printStackTrace(); // prints error(s)
				System.exit(0); // Exits entire program
			}
		}
	}
	
	// write data to debug file
	public void writeData()
	{		
		try
		{
			// create new output file
			FileWriter outfile = new FileWriter("debug1.txt");
			
			// write all words to file
			for (int index = 0; index < this.words.size(); index++)
			{
				outfile.write(this.words.elementAt(index).getValue() + "\n");
			}
			
			// close file
			outfile.close();
		}
		
		// error catch in case something goes wrong
		catch (IOException e)
		{
			System.out.println("An error occured.");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}
	}
}
