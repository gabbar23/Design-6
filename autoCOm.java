// Time Complexity: O(N * log N) for insertion and sorting where N is the length of the input word.
// Space Complexity: O(N * L) where N is the number of words and L is the average length of words.
class AutocompleteSystem {

    // TrieNode class to represent each node in the Trie
    class TrieNode {
        HashMap<Character, TrieNode> children; // Map from character to the corresponding TrieNode
        ArrayList<String> nodeWords; // List of words that pass through this node

        public TrieNode() {
            this.children = new HashMap<>(); // Initialize children map
            this.nodeWords = new ArrayList<>(); // Initialize the list of words
        }
    }

    // Insert a word into the Trie
    private void insertTrie(TrieNode root, String word) {
        TrieNode current = root; // Start at the root
        for (char c : word.toCharArray()) {
            // If the character c doesn't exist in children, add a new TrieNode
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c); // Move to the next node
            
            // If the word is not already in the node's word list, add it
            if (!current.nodeWords.contains(word)) {
                current.nodeWords.add(word);
            }

            // Sort nodeWords based on frequency and lexicographical order
            Collections.sort(current.nodeWords, (a, b) -> {
                if (dataMap.get(a).equals(dataMap.get(b))) {
                    return a.compareTo(b); // Lexicographical order if frequencies are the same
                }
                return dataMap.get(b) - dataMap.get(a); // Higher frequency has higher priority
            });
                
            // If the nodeWords list exceeds 3, remove the least relevant word
            if (current.nodeWords.size() > 3) {
                current.nodeWords.remove(current.nodeWords.size() - 1);
            }
        }
    }

    // Search for words in the Trie that match the prefix
    private ArrayList<String> searchTrie(TrieNode root, String word) {
        TrieNode current = root; // Start at the root
        for (char c : word.toCharArray()) {
            // If the character c is not in children, return an empty list (no match found)
            if (current.children.get(c) == null) {
                return new ArrayList<>();
            }
            current = current.children.get(c); // Move to the next node
        }
        return current.nodeWords; // Return the list of words that match the prefix
    }

    // Class attributes
    StringBuilder input; // To store the current input prefix
    HashMap<String, Integer> dataMap; // Map to store word frequencies (hotness)
    TrieNode root; // Root node of the Trie

    // Constructor
    public AutocompleteSystem(String[] sentences, int[] times) {
        this.input = new StringBuilder(); // Initialize input buffer
        this.dataMap = new HashMap<>(); // Initialize the hotness map
        this.root = new TrieNode(); // Initialize the root of the Trie
        for (int i = 0; i < times.length; i++) {
            int count = times[i];
            dataMap.put(sentences[i], dataMap.getOrDefault(sentences[i], 0) + count); // Update hotness
            insertTrie(root, sentences[i]); // Insert sentence into Trie
        }
    }

    // Method to process input characters
    public List<String> input(char c) {
        // If end of input (special character '#')
        if (c == '#') {
            String inputString = input.toString();
            // Update the frequency of the complete word
            dataMap.put(inputString, dataMap.getOrDefault(inputString, 0) + 1);
            insertTrie(root, inputString); // Insert the complete word into the Trie
            input = new StringBuilder(); // Reset input buffer
            return new ArrayList<>(); // Return empty list as no suggestions are needed
        }

        input.append(c); // Append the current character to the input prefix
        // Get all words in Trie that match the current input prefix
        return searchTrie(root, input.toString());
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */
