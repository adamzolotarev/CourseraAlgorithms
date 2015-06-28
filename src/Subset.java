public class Subset {
    public static void main (String[] args) {
        int numberOfSubsets = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while(!StdIn.isEmpty())        {
            queue.enqueue(StdIn.readString());
        }

        for(int i=0; i<numberOfSubsets; i++){
            StdOut.print(queue.dequeue());
        }
    }
}