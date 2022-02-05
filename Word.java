public class Word implements Comparable<Word>{
	private String value; // actual word being stored
	private int countPT; // count of word on Panopto captions
	private int countYT; // count of word on YouTube captions
	
	// constructor for Word
	public Word(String value)
	{
		this.value = value.toLowerCase();
		this.countPT = 0;
		this.countYT = 0;
	}

	// getters and setters
	public String getValue() { return value; }
	public void setValue(String value) { this.value = value; }
	
	public int getCountPT() { return countPT; }
	public void setCountPT(int countPT) { this.countPT = countPT; }
	
	public int getCountYT() { return countYT; }
	public void setCountYT(int countYT) { this.countYT = countYT; }

	// toString for printing
	public String toString() {
		return "----------\n" + value + "\n" + countPT + "\n" + countYT + "\n";
	}
	
	// comparable for sorting function
	public int compareTo(Word c){
		return this.value.compareTo(c.getValue());
	}
}
