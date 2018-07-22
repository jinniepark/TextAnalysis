import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class TextAnalysis {

	public static void main(String[] args) throws FileNotFoundException {

		// System.out.println(getTotalNumberOfWords());
		// System.out.println(getTotalUniqueWords());
		// System.out.println(get20MostFrequentWords());
//		 System.out.println(get20MostInterestingFrequentWords());
		// System.out.println(get20LeastFrequentWords());
		// System.out
		// .println(getChapterQuoteAppears("My good opinion once lost, is lost forever."));
		// System.out.println(generateSentence());
		// System.out.println(getAutocompleteSentence("What is"));
		// System.out
		// .println(findClosestMatchingQuote("is my bad opinion lost, once lost"));
		 toString(getFrequencyOfWord("love"));


	}

	public static File getPrideAndPrejudiceFile() {
		File file = new File("1342.txt");
		return file;
	}

	public static String removeSpecialChar(String s) {
		s = s.replaceAll("[\\+\\^:,;\",(,)]", "").toLowerCase();
		return s;
	}

	public static String removePunctuations(String s) {
		s = s.replaceAll("[-, ?, !, .]", " ").toLowerCase();
		return s;

	}

	public static int getTotalNumberOfWords() throws FileNotFoundException {
		File file = getPrideAndPrejudiceFile();
		int count = 0;
		try (Scanner sc = new Scanner(new FileInputStream(file))) {
			while (sc.hasNext()) {
				sc.next();
				count++;
			}
		}
		return count;
	}

	public static int getTotalUniqueWords() throws FileNotFoundException {
		File file = getPrideAndPrejudiceFile();
		HashSet<String> uniqueWords = new HashSet<String>();
		try (Scanner sc = new Scanner(new FileInputStream(file))) {
			while (sc.hasNext()) {
				// remove special characters and make everything lowercase
				String currWord = removeSpecialChar(sc.next());
				currWord = removePunctuations(currWord);
				uniqueWords.add(currWord);
			}
		}
		return uniqueWords.size();
	}

	public static PriorityQueue<String> constructMaxHeap(
			final Map<String, Integer> wordFrequency) {
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
		return wordRanks;
	}

	public static PriorityQueue<String> constructMinHeap(
			final Map<String, Integer> wordFrequency) {
		PriorityQueue<String> wordRanks = new PriorityQueue<String>(
				new Comparator<String>() {
					@Override
					public int compare(String word1, String word2) {
						if (wordFrequency.get(word1) == wordFrequency
								.get(word2)) {
							return word2.compareTo(word1);
						}
						return wordFrequency.get(word1)
								- wordFrequency.get(word2);
					}
				});
		wordRanks.addAll(wordFrequency.keySet());
		return wordRanks;
	}

	public static ArrayList<HashMap<String, Integer>> get20MostFrequentWords()
			throws FileNotFoundException {
		// Map that contains string and its frequency
		final Map<String, Integer> wordFrequency = new HashMap<String, Integer>();
		File file = getPrideAndPrejudiceFile();
		try (Scanner sc = new Scanner(new FileInputStream(file))) {
			while (sc.hasNext()) {
				// remove special characters and make everything lowercase
				String currString = removeSpecialChar(sc.next());
				currString = removePunctuations(currString);
				if (wordFrequency.containsKey(currString)) {
					int currStringFrequency = wordFrequency.get(currString);
					wordFrequency.put(currString, currStringFrequency + 1);
				} else {
					wordFrequency.put(currString, 1);
				}
			}
		}

		// Build max heap using the frequency map
		PriorityQueue<String> wordRanks = constructMaxHeap(wordFrequency);

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

	public static ArrayList<HashMap<String, Integer>> get20MostInterestingFrequentWords()
			throws FileNotFoundException {

		HashSet<String> top100CommonWords = getNMostCommonWords(100, "1-1000.txt");
		final Map<String, Integer> wordFrequency = new HashMap<String, Integer>();
		File file = getPrideAndPrejudiceFile();
		try (Scanner sc = new Scanner(new FileInputStream(file))) {
			while (sc.hasNext()) {
				String currString = removeSpecialChar(sc.next());
				currString = removePunctuations(currString);
				// if the string is not one of top common words, add to the map
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

		PriorityQueue<String> wordRanks = constructMaxHeap(wordFrequency);

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

	public static ArrayList<HashMap<String, Integer>> get20LeastFrequentWords()
			throws FileNotFoundException {
		final Map<String, Integer> wordFrequency = new HashMap<String, Integer>();
		File file = getPrideAndPrejudiceFile();
		try (Scanner sc = new Scanner(new FileInputStream(file))) {
			while (sc.hasNext()) {
				String tmp = removeSpecialChar(sc.next());
				tmp = removePunctuations(tmp);
				String[] afterModifying = tmp.split(" ");
				String currString = "";
				if (afterModifying.length > 0) {
					currString = afterModifying[0];
				}
				if (wordFrequency.containsKey(currString)) {
					int currStringFrequency = wordFrequency.get(currString);
					wordFrequency.put(currString, currStringFrequency + 1);
				} else {
					wordFrequency.put(currString, 1);
				}
			}
		}

		// Construct min heap using word frequency map
		PriorityQueue<String> wordRanks = constructMinHeap(wordFrequency);

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

	public static int[] getFrequencyOfWord(String word)
			throws FileNotFoundException {
		File file = getPrideAndPrejudiceFile();
		String content = removeSpecialChar(new Scanner(file)
				.useDelimiter("\\Z").next());
		content = removePunctuations(content);

		String[] contentsPerChapter = content.split("chapter");
		int[] res = new int[contentsPerChapter.length - 1];

		for (int i = 1; i < contentsPerChapter.length; i++) {
			String[] temp = contentsPerChapter[i].split(" ");
			int counter = 0;
			for (int j = 0; j < temp.length; j++) {
				if (temp[j].toLowerCase().equals(word.toLowerCase())) {
					counter++;
				}
			}
			res[i - 1] = counter;
		}
		return res;
	}

	public static int getChapterQuoteAppears(String quote)
			throws FileNotFoundException {

		File file = getPrideAndPrejudiceFile();
		String content = new Scanner(file).useDelimiter("\\Z").next();
		content = content.replace("\n", " ").replace("\r", "").toLowerCase();
		String[] contentsPerChapter = content.split("chapter");

		for (int i = 1; i < contentsPerChapter.length; i++) {
			String textInChapter = contentsPerChapter[i];
			if (strStr(textInChapter, quote.toLowerCase()) != -1) {
				return i;
			}
		}
		return -1;
	}

	public static String generateSentence() throws FileNotFoundException {
		File file = getPrideAndPrejudiceFile();
		Random rand = new Random();
		Map<String, ArrayList<String>> dict = new HashMap<String, ArrayList<String>>();
		String sentence = "The ";
		String word = "the";
		for (int i = 0; i < 19; i++) {
			try (Scanner sc = new Scanner(new FileInputStream(file))) {
				while (sc.hasNext()) {
					String temp = sc.next();
					temp = temp.replaceAll("[\\+\\^:,;\",(,)\\.\\!]", "");

					if (temp.toLowerCase().equals(word.toLowerCase())) {
						if (dict.containsKey(word)) {
							ArrayList<String> arrList = dict.get(word);
							arrList.add(sc.next().replaceAll(
									"[\\+\\^:,;\",(,)\\.\\!]", ""));
							dict.put(word, arrList);
						} else {
							ArrayList<String> arrList = new ArrayList<String>();
							arrList.add(sc.next().replaceAll(
									"[\\+\\^:,;\",(,)\\.\\!]", ""));
							dict.put(word, arrList);
						}
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
				topNList.add(sc.next().toLowerCase());
				counter++;
			}
		}
		return topNList;
	}

	public static ArrayList<String> getAutocompleteSentence(
			String startOfSentence) throws FileNotFoundException {

		File file = getPrideAndPrejudiceFile();
		Trie t = new Trie();
		String content = new Scanner(file).useDelimiter("\\Z").next();
		content = content.replace("\n", " ").replace("\r", "")
				.replaceAll("[\"]", "");
		String[] contentSentences = content.split("\\b[.!?]\\s+");

		for (int i = 0; i < contentSentences.length; i++) {
			String sentence = contentSentences[i];
			t.insert(sentence.toLowerCase());
		}

		ArrayList<String> a = (ArrayList<String>) t
				.autocomplete(startOfSentence.toLowerCase());
		return a;
	}

	public static int findClosestMatchingQuote(String s)
			throws FileNotFoundException {
		File file = getPrideAndPrejudiceFile();
		String content = new Scanner(file).useDelimiter("\\Z").next();
		content = content.replace("\n", "").replace("\r", " ")
				.replaceAll("[\"]", "");

		String[] contentsPerChapter = content.split("Chapter");
		int[] scores = new int[contentsPerChapter.length];
		for (int i = 1; i < contentsPerChapter.length; i++) {
			String textInChapter = contentsPerChapter[i];
			String[] sentences = textInChapter.split("\\b[.!?]\\s+");
			int chapterMax = -1;
			for (int j = 0; j < sentences.length; j++) {
				String[] aligned = align(sentences[j].toLowerCase(),
						s.toLowerCase());
				String[] aligned1 = aligned[0].split(" ");
				String[] aligned2 = aligned[1].split(" ");
				int counter = 0;
				for (int k = 0; k < aligned1.length; k++) {
					if (aligned1[k].equals(aligned2[k])) {
						counter++;
					}
				}
				if (counter > chapterMax) {
					chapterMax = counter;
				}
			}
			scores[i] = chapterMax;

		}

		int tempIndx = 0;
		for (int i = 1; i < scores.length; i++) {
			if (scores[i] > scores[tempIndx]) {
				tempIndx = i;
			}
		}
		return tempIndx;
	}

	public static void toString(int[] arr) {
		String s = "";
		for (int i = 0; i < arr.length; i++) {
			s += arr[i] + ",";
		}
		System.out.println(s);
	}

	public static int strStr(String content, String quote) {
		for (int i = 0;; i++) {
			for (int j = 0;; j++) {
				if (j == quote.length())
					return i;
				if (i + j == content.length())
					return -1;
				if (quote.charAt(j) != content.charAt(i + j))
					break;
			}
		}
	}

	public static String[] align(String a, String b) {
		/* Needleman–Wunsch algorithm for aligning strings */

		String[] wordsInA = a.split(" ");
		String[] wordsInB = b.split(" ");

		int[][] alignMatrix = new int[wordsInA.length + 1][wordsInB.length + 1];

		for (int i = 0; i <= wordsInA.length; i++)
			alignMatrix[i][0] = i;

		for (int i = 0; i <= wordsInB.length; i++)
			alignMatrix[0][i] = i;

		for (int i = 1; i <= wordsInA.length; i++) {
			for (int j = 1; j <= wordsInB.length; j++) {

				if (wordsInA[i - 1].equals(wordsInB[j - 1])) {
					alignMatrix[i][j] = alignMatrix[i - 1][j - 1];
				} else {
					alignMatrix[i][j] = Math.min(alignMatrix[i - 1][j],
							alignMatrix[i][j - 1]) + 1;
				}
			}
		}

		StringBuilder alignA = new StringBuilder(), alignB = new StringBuilder();

		for (int i = wordsInA.length, j = wordsInB.length; i > 0 || j > 0;) {
			if (i > 0 && alignMatrix[i][j] == alignMatrix[i - 1][j] + 1) {
				alignA.append(wordsInA[--i]);
				alignA.append(" ");
				alignB.append("-");
				alignB.append(" ");

			} else if (j > 0 && alignMatrix[i][j] == alignMatrix[i][j - 1] + 1) {
				alignB.append(wordsInB[--j]);
				alignB.append(" ");
				alignA.append("-");
				alignA.append(" ");
			} else if (i > 0 && j > 0
					&& alignMatrix[i][j] == alignMatrix[i - 1][j - 1]) {
				alignA.append(wordsInA[--i]);
				alignA.append(" ");
				alignB.append(wordsInB[--j]);
				alignB.append(" ");
			}
		}

		return new String[] { alignA.toString(), alignB.toString() };
	}

}
