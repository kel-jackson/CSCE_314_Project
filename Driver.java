public class Driver {
	// create static data members for FileIO and Hashing for static methods in Testing.java
	static private FileIO newFileIO = new FileIO();
	static private Hashing newHashMap;
	
	static public void readFiles()
	{
		// read data from both text files
		newFileIO.readTextFile("PT1.txt");
		newFileIO.readTextFile("YT1.txt");
		
		// after initiallizing FileIO, initialize Hashing
		newHashMap = new Hashing(newFileIO);
	}
	
	static public void debug() { newFileIO.writeData(); }
	
	static public void createListEqual() { newHashMap.writeListEqual(); }
	
	static public void createListDiff() { newHashMap.writeListDifference(); }
	
	static public void createEqual() { newHashMap.writeEqual(); }
	
	static public void createDiff() { newHashMap.writeDifference(); }
}
