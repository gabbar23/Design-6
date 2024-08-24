// Time Complexity: O(1) for each operation (get, check, release)
// Space Complexity: O(maxNumbers) for storing the numbers in the queue and set
class PhoneDirectory {
    // Queue to maintain the list of available numbers
    Queue<Integer> que;
    
    // HashSet to quickly check if a number is available or not
    HashSet<Integer> set;

    // Constructor initializes the PhoneDirectory with a maximum number of numbers
    public PhoneDirectory(int maxNumbers) {
        this.que = new LinkedList<>();
        this.set = new HashSet<>();
        
        // Add all numbers from 0 to maxNumbers - 1 to the queue and set
        for (int i = 0; i < maxNumbers; i++) {
            que.add(i);
            set.add(i);
        }
    }

    // Method to get the next available number
    public int get() {
        // If no numbers are available, return -1
        if (que.isEmpty())
            return -1;
        
        // Remove the number from the queue and set, then return it
        int newNumber = que.poll();
        set.remove(newNumber);
        return newNumber;
    }

    // Method to check if a specific number is available
    public boolean check(int number) {
        // Return true if the number is in the set, meaning it's available
        return set.contains(number);
    }

    // Method to release a number back into the pool of available numbers
    public void release(int number) {
        // If the number is not already available, add it back to the set and queue
        if (!set.contains(number)) {
            set.add(number);
            que.add(number);
        }
    }
}

/**
 * Your PhoneDirectory object will be instantiated and called as such:
 * PhoneDirectory obj = new PhoneDirectory(maxNumbers);
 * int param_1 = obj.get();
 * boolean param_2 = obj.check(number);
 * obj.release(number);
 */
