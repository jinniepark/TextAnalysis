import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

class TrieNode {
	String data;
	LinkedList<TrieNode> children;
	TrieNode parent;
	boolean isEnd;

	public TrieNode(String s) {
		data = s;
		children = new LinkedList<>();
		isEnd = false;
	}

	public TrieNode getChild(String s) {

		if (children != null) {

			for (TrieNode eachChild : children) {		
				if (eachChild.data.equals(s)) {
					return eachChild;
				}
			}
		}
		return null;
	}

	protected List<String> getWords() {

		List<String> list = new ArrayList<>();
		if (isEnd) {
			list.add(toString());
		}

		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i) != null) {
					list.addAll(children.get(i).getWords());
				}
			}
		}
		return list;
	}

	public String toString() {
		if (parent == null) {
			return "";
		} else {
			return parent.toString() + " " + data;
		}
	}
}

class Trie {
	private TrieNode root;

	public Trie() {
		root = new TrieNode(" ");
	}

	public void insert(String sentence) {
		if (search(sentence) == true)
			return;

		TrieNode current = root;
		TrieNode pre;
		for (String s : sentence.split(" ")) {
			pre = current;
			TrieNode child = current.getChild(s);
			if (child != null) {
				current = child;
				child.parent = pre;
			} else {
				current.children.add(new TrieNode(s));
				current = current.getChild(s);
				current.parent = pre;
			}
		}
		current.isEnd = true;
	}

	public boolean search(String sentence) {
		TrieNode current = root;
		for (String s : sentence.split(" ")) {
			if (current.getChild(s) == null) {
				return false;
			} else {
				current = current.getChild(s);
			}
		}
		if (current.isEnd == true) {
			return true;
		}
		return false;
	}

	public List<String> autocomplete(String prefix) {
		TrieNode lastNode = root;
		String[] wordsInPrefix = prefix.split(" ");

		for (int i = 0; i < wordsInPrefix.length; i++) {
			lastNode = lastNode.getChild(wordsInPrefix[i]);
			if (lastNode == null) {
				return new ArrayList<String>();
			}
		}
		return lastNode.getWords();
	}
}