import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.util.Date;

public class BasicWordSearcher {
	//Sorts and searches a big list of words using insertion sorting and binary searching
	public static void main(String[] args) {
		BasicWordSearcher b = new BasicWordSearcher(1000, "bigwords.txt", "a", 10000000);
	}

	private ArrayList<String> list;
	private int listSize;
	private Random r;
	
	public BasicWordSearcher(int s, String filename, String term, int runs) {
		r = new Random();
		listSize = s;
		list = new ArrayList<String>(listSize);
		fillList(filename);
		
		long sortStartTime = new Date().getTime();
		insertionSortList();
		long sortFinishTime = new Date().getTime();
		long sortTime = sortFinishTime - sortStartTime;
		System.out.println("Sort time: "+sortTime+" milliseconds");
		
		int index = -1;
		long searchStartTime = new Date().getTime();
		for (int i = 0; i < runs; i++) {
			index = binarySearchList(term.toLowerCase(), 0, getListSize() - 1);
		}
		long searchFinishTime = new Date().getTime();
		long searchTime = searchFinishTime - searchStartTime;
		System.out.println("Search time: "+searchTime+" milliseconds");
		
		System.out.println(term+" at "+index);
	}
	
	public void fillList(String filename) {
		ArrayList<String> imp = new ArrayList<String>();
		
		//Import first #size number of words from the text file: 
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			try {
				String line;
				int i = 0;
				while ((line = br.readLine()) != null && i < getListSize()) {
					imp.add(line.toLowerCase());
					i++;
				}
				try {
					br.close();
				} catch (IOException e) { System.out.println("Failed to close"); }
			} catch (IOException e) { System.out.println("Could not import!"); }
		} catch (FileNotFoundException e) { System.out.println("Could not find file"); }
		
		//Shuffle the list and add it to the main list:
		int randInd;
		while (imp.size() > 0) {
			randInd = r.nextInt(imp.size());
			getList().add(imp.get(randInd));
			imp.remove(randInd);
		}
	}
	
	public void insertionSortList() {
		//Use insertion sort to sort the list
		String valueToInsert;
		int hole;
		for (int i = 0; i < getListSize(); i++) {
			valueToInsert = getList().get(i);
			hole = i;
			while (hole > 0 && isOutOfOrder(valueToInsert, getList().get(hole - 1))) {
				getList().set(hole, getList().get(hole - 1));
				hole--;
			}
			getList().set(hole, valueToInsert);
		}
	}
	
	public boolean isOutOfOrder(String a, String b) {
		//Ugly. Abusing collection's sort method to alphabetize
		ArrayList<String> firstList = new ArrayList<String>(2);
		firstList.add(a);
		firstList.add(b);
		ArrayList<String> secondList = new ArrayList<String>(2);
		secondList.add(a);
		secondList.add(b);
		java.util.Collections.sort(secondList);
		if (!firstList.get(0).equals(secondList.get(0)))
			return false;
		return true;
	}
	
	public int binarySearchList(String term, int start, int fin) {
		//Base case:
		if (start == fin || fin - start == 1) {
			System.out.println(fin);
			System.out.println(start);
			if (getList().get(start).equals(term))
				return start;
			else if (getList().get(fin).equals(term))
				return fin;
			else
				return -1;
		}
		
		//Determine the midpoint, and check if it's correct, to potentially shave time
		int mid = ((fin - start) / 2) + start;
		if (term.equals(getList().get(mid)))
			return mid;
		
		//Recursively call the search method
		if (isOutOfOrder(term, getList().get(mid)))
			return binarySearchList(term, start, mid);
		else
			return binarySearchList(term, mid, fin);
	}
	
	public ArrayList<String> getList() {
		return list;
	}
	
	public int getListSize() {
		return listSize;
	}
}