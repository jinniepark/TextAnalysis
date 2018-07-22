import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class TextAnalysis {

	public static void main(String[] args) throws FileNotFoundException {
		/*
		TODO: 
		
		1) fix the parsing so it takes care of the special characters
		   String s = ".hi";
		   s = s.replace('.', Character.MIN_VALUE);
		   System.out.println(s);
		   
		
		2) quotes that go over one line
		
				
		*/
		
//		System.out.println(getTotalNumberOfWords("1342.txt"));
//		System.out.println(getTotalUniqueWords("1342.txt"));
//		System.out.println(get20MostFrequentWords("1342.txt"));
//		System.out.println(get20MostInterestingFrequentWords(100, "1342.txt"));
//		System.out.println(get20LeastFrequentWords("1342.txt"));
//		toString(getFrequencyOfWord("in", "1342.txt"));
		System.out.println(getChapterQuoteAppears("At five o'clock the two ladies retired to dress, and at half-past six", "1342.txt"));

		


	}

	public static int getTotalNumberOfWords(String textfile)
			throws FileNotFoundException {
		File file = new File(textfile);
		int count = 0;
		try (Scanner sc = new Scanner(new FileInputStream(file))) {
			while (sc.hasNext()) {
				sc.next();
				count++;
			}
		}
		return count;
	}

	public static int getTotalUniqueWords(String textfile)
			throws FileNotFoundException {
		File file = new File(textfile);
		HashSet<String> uniqueWords = new HashSet<String>();
		try (Scanner sc = new Scanner(new FileInputStream(file))) {
			while (sc.hasNext()) {
				uniqueWords.add(sc.next());
			}
		}
		return uniqueWords.size();
	}

	public static ArrayList<HashMap<String, Integer>> get20MostFrequentWords(
			String textfile) throws FileNotFoundException {
		final Map<String, Integer> wordFrequency = new HashMap<String, Integer>();
		File file = new File(textfile);
		try (Scanner sc = new Scanner(new FileInputStream(file))) {
			while (sc.hasNext()) {
				String currString = sc.next();
				if (wordFrequency.containsKey(currString)) {
					int currStringFrequency = wordFrequency.get(currString);
					wordFrequency.put(currString, currStringFrequency + 1);
				} else {
					wordFrequency.put(currString, 1);
				}
			}
		}

		PriorityQueue<String> wordRanks = new PriorityQueue<String>(
				new Comparator<String>() {
					@Override
					public int compare(String word1, String word2) {
						if (wordFrequency.get(word1) == wordFrequency
								.get(word2)) {
							return word1.compareTo(word2);
						}
						return wordFrequency.get(word2)
								- wordFrequency.get(word1);
					}
				});
		wordRanks.addAll(wordFrequency.keySet());
		ArrayList<HashMap<String, Integer>> top20Words = new ArrayList<HashMap<String, Integer>>();
		for (int i = 0; i < 20; i++) {
			HashMap<String, Integer> temp = new HashMap<>();
			if (!wordRanks.isEmpty()) {
				String wordProcessing = wordRanks.poll();
				temp.put(wordProcessing, wordFrequency.get(wordProcessing));
				top20Words.add(temp);
			}
		}
		return top20Words;
	}

	public static ArrayList<HashMap<String, Integer>> get20MostInterestingFrequentWords(
			int N, String textfile) throws FileNotFoundException {
		HashSet<String> top100CommonWords = getNMostCommonWords(N, "1-1000.txt");
		final Map<String, Integer> wordFrequency = new HashMap<String, Integer>();
		File file = new File(textfile);
		try (Scanner sc = new Scanner(new FileInputStream(file))) {
			while (sc.hasNext()) {
				String currString = sc.next();
				if (!top100CommonWords.contains(currString)) {
					if (wordFrequency.containsKey(currString)) {
						int currStringFrequency = wordFrequency.get(currString);
						wordFrequency.put(currString, currStringFrequency + 1);
					} else {
						wordFrequency.put(currString, 1);
					}
				}
			}
		}

		PriorityQueue<String> wordRanks = new PriorityQueue<String>(
				new Comparator<String>() {
					@Override
					public int compare(String word1, String word2) {
						if (wordFrequency.get(word1) == wordFrequency
								.get(word2)) {
							return word1.compareTo(word2);
						}
						return wordFrequency.get(word2)
								- wordFrequency.get(word1);
					}
				});
		wordRanks.addAll(wordFrequency.keySet());
		ArrayList<HashMap<String, Integer>> top20Words = new ArrayList<HashMap<String, Integer>>();
		for (int i = 0; i < 20; i++) {
			HashMap<String, Integer> temp = new HashMap<>();
			if (!wordRanks.isEmpty()) {
				String wordProcessing = wordRanks.poll();
				temp.put(wordProcessing, wordFrequency.get(wordProcessing));
				top20Words.add(temp);
			}
		}
		return top20Words;
	}

	public static ArrayList<HashMap<String, Integer>> get20LeastFrequentWords(
			String textfile) throws FileNotFoundException {
		final Map<String, Integer> wordFrequency = new HashMap<String, Integer>();
		File file = new File(textfile);
		try (Scanner sc = new Scanner(new FileInputStream(file))) {
			while (sc.hasNext()) {
				String currString = sc.next();
				if (wordFrequency.containsKey(currString)) {
					int currStringFrequency = wordFrequency.get(currString);
					wordFrequency.put(currString, currStringFrequency + 1);
				} else {
					wordFrequency.put(currString, 1);
				}
			}
		}

		PriorityQueue<String> wordRanks = new PriorityQueue<String>(
				new Comparator<String>() {
					@Override
					public int compare(String word1, String word2) {
						if (wordFrequency.get(word1) == wordFrequency
								.get(word2)) {
							return word1.compareTo(word2);
						}
						return wordFrequency.get(word1)
								- wordFrequency.get(word2);
					}
				});
		wordRanks.addAll(wordFrequency.keySet());
		ArrayList<HashMap<String, Integer>> least20Words = new ArrayList<HashMap<String, Integer>>();
		for (int i = 0; i < 20; i++) {
			HashMap<String, Integer> temp = new HashMap<>();
			if (!wordRanks.isEmpty()) {
				String wordProcessing = wordRanks.poll();
				temp.put(wordProcessing, wordFrequency.get(wordProcessing));
				least20Words.add(temp);
			}
		}
		return least20Words;
	}

	public static int[] getFrequencyOfWord(String word, String textfile)
			throws FileNotFoundException {

		String content = new Scanner(new File(textfile)).useDelimiter("\\Z")
				.next();
		String[] contentsPerChapter = content.split("Chapter");
		int[] res = new int[contentsPerChapter.length - 1];

		for (int i = 1; i < contentsPerChapter.length; i++) {
			String[] temp = contentsPerChapter[i].split(" ");
			int counter = 0;
			for (int j = 0; j < temp.length; j++) {
				if (temp[j].equals(word)) {
					counter++;
				}

			}
			res[i - 1] = counter;
		}

		return res;
	}

	public static int getChapterQuoteAppears(String quote, String textfile)
			throws FileNotFoundException {

		String content = new Scanner(new File(textfile)).useDelimiter("\\Z")
				.next();
		String[] contentsPerChapter = content.split("Chapter");
//		System.out.println(contentsPerChapter[8].substring(0,80));
//		String[] quoteArray = quote.split(" ");
//		int chapter = -1;

		for (int i = 1; i < contentsPerChapter.length; i++) {
			String textInChapter = contentsPerChapter[i];
			textInChapter = textInChapter.replaceAll("[\\n\\t]", "");
//			System.out.print(textInChapter);
			String[] sentences = textInChapter.split("(?<=[a-z])\\.\\s+");
			System.out.println(sentences[0]);
			if(strStr(textInChapter, quote) != -1) {
				return i;
			}
			
		}

		return -1;
	}
	
	public static String generateSentence(File file)
			throws FileNotFoundException {

		Random rand = new Random();
		Map<String, ArrayList<String>> dict = new HashMap<String, ArrayList<String>>();
		String sentence = "The ";
		String word = "the";
		for (int i = 0; i < 19; i++) {
			try (Scanner sc = new Scanner(new FileInputStream(file))) {
				while (sc.hasNext()) {
					if (sc.next().toLowerCase().equals(word.toLowerCase())) {
						ArrayList<String> arrList;
						if (dict.containsKey(word)) {
							arrList = dict.get(word);
						} else {
							arrList = new ArrayList<String>();
						}
						arrList.add(sc.next());
						dict.put(word, arrList);
						// add relevant words to the array
					}
				}
			}
			int randomNum = rand.nextInt(dict.get(word).size());
			word = dict.get(word).get(randomNum).toLowerCase();
			sentence += word + " ";
		}
		return sentence;
	}

	public static HashSet<String> getNMostCommonWords(int N, String listFile)
			throws FileNotFoundException {
		HashSet<String> topNList = new HashSet<String>();
		File file = new File(listFile);
		int counter = 0;
		try (Scanner sc = new Scanner(new FileInputStream(file))) {
			while (counter < N && sc.hasNext()) {
				topNList.add(sc.next());
				counter++;
			}
		}
		return topNList;
	}

	public static void toString(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ",");
		}
	}

	public static int strStr(String haystack, String needle) {
		  for (int i = 0; ; i++) {
		    for (int j = 0; ; j++) {
		      if (j == needle.length()) return i;
		      if (i + j == haystack.length()) return -1;
		      if (needle.charAt(j) != haystack.charAt(i + j)) break;
		    }
		  }
		}

}
