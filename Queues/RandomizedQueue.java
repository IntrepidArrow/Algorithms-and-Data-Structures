/* *****************************************************************************
 *  Name: Abhimukth Chaudhuri
 *  Date: June 9 2020
 *  Description: Assignment 2 - Queues
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] randomizedQueue;
    private int count;
    private int tail;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.randomizedQueue = (Item[]) new Object[1];
        this.count = 0;
        this.tail = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null or empty!");
        }
        if (randomizedQueue.length == tail) {
            resize(2 * randomizedQueue.length);
        }
        randomizedQueue[tail++] = item;
        count++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty!");
        }
        int randomIndex = randomIndex();
        Item itemToRemove = randomizedQueue[randomIndex];
        randomizedQueue[randomIndex] = null;
        count--;

        if (count > 0 && count == randomizedQueue.length / 4) {
                resize(randomizedQueue.length / 2);
        }

        return itemToRemove;

        /*
        Chasing for correct tail pointer causes a lot of Timing and some Deque Operations to fail.
        Resizing of the array takes care of instantiating the pointer in its correct position.
        Chasing for pointers is O(N) in its worst-case for last inputs of N. This violates the
            constant time requirement for the dequeue method for the assignment.

        Code for chasing tail pointer is below:
         */

        // update correct position of tail if tail-1 element is removed
        // if (isEmpty()) {
        //     tail = 0;
        // }
        // else if (count > 0 && count == randomizedQueue.length / 4) {
        //     resize(randomizedQueue.length / 2);
        // }
        // else {
        //     for (int i = randomizedQueue.length - 1; i >= 1; i--) {
        //         if (randomizedQueue[i-1] != null) {
        //             tail = i;
        //         }
        //     }
        // }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty!");
        }
        int randomIndex = randomIndex();
        return randomizedQueue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int index = 0;
        for (int i = 0; i < randomizedQueue.length; i++) {
            if (randomizedQueue[i] != null) {
                copy[index] = randomizedQueue[i];
                index++;
            }
        }
        randomizedQueue = copy;
        tail = index;
    }

    // returns a valid index position of non-null item in array
    private int randomIndex() {
        Item item;
        int index;
        do {
            index = StdRandom.uniform(randomizedQueue.length);
            item = randomizedQueue[index];
        } while (item == null);
        return index;
    }

    private class QueueIterator implements Iterator<Item> {

        // private final RandomizedQueue<Item> queueCopy = new RandomizedQueue<>();
        private final int[] randomIndices;
        private int pointer;

        public QueueIterator() {
            this.pointer = 0;
            this.randomIndices = new int[count];

            int index = 0;
            for (int i = 0; i < randomizedQueue.length; i++) {
                if (randomizedQueue[i] != null) {
                    randomIndices[index] = i;
                    index++;
                }
            }

            StdRandom.shuffle(randomIndices);
        }

        public boolean hasNext() {
            // return !queueCopy.isEmpty();
            return pointer < count;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to return by the iterator");
            }
            return randomizedQueue[randomIndices[pointer++]];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove method not supported by iterator.");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<Integer> testQueue = new RandomizedQueue<>();
        // StdOut.println(testQueue.isEmpty());

        testQueue.enqueue(2);
        // testQueue.dequeue();
        testQueue.enqueue(3);
        testQueue.enqueue(5);
        // testQueue.dequeue();
        testQueue.enqueue(7);
        testQueue.enqueue(11);
        testQueue.enqueue(13);

        StdOut.println(testQueue.sample());

        testQueue.dequeue();
        testQueue.dequeue();
        testQueue.dequeue();
        testQueue.dequeue();
        // testQueue.dequeue();

        for (Integer integer :  testQueue) {
            StdOut.print(integer + " ");
        }
        StdOut.println();

        StdOut.println(testQueue.size());

        StdOut.println(testQueue.sample());
    }
}
