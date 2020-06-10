/* *****************************************************************************
 *  Name: Abhimukth Chaudhuri
 *  Date: June 9, 2020
 *  Description: Assignment 2 - Queues
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private DequeNode head;
    private DequeNode tail;
    private int size;

    // construct an empty deque
    public Deque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be empty!");
        }

        DequeNode oldHead = head;
        head = new DequeNode(item);
        head.next = oldHead;
        if (tail == null) { // Case when node is the first item in the collection
            tail = head;
        }
        else {
            head.next.previous = head;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be empty!");
        }

        DequeNode oldTail = tail;
        tail = new DequeNode(item);
        tail.previous = oldTail;
        if (head == null) {
            head = tail;
        }
        else {
            tail.previous.next = tail;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty! Nothing to remove!");
        }

        Item headItem = head.item;
        if (head.equals(tail)) {
            head = null;
            tail = null;
        }
        else {
            head = head.next;
            head.previous = null;
        }
        size--;
        return headItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty! Nothing to remove!");
        }

        Item tailItem = tail.item;
        if (tail.equals(head)) {
            head = null;
            tail = null;
        }
        else {
            tail = tail.previous;
            tail.next = null;
        }
        size--;
        return tailItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private DequeNode current = head;

        // return false if the next element does not exist
        public boolean hasNext() {
            return current != null;
        }

        // return current data and advance pointer in data structure
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more objects in Deque.");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "Remove operation not supported for Deque iterator.");
        }
    }

    private class DequeNode {
        private final Item item;
        private DequeNode next;
        private DequeNode previous;

        public DequeNode(Item item) {
            this.item = item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<Integer> testDeque = new Deque<>();

        testDeque.addLast(3);
        testDeque.addLast(5);
        testDeque.addLast(7);
        testDeque.addLast(11);
        testDeque.addLast(13);

        testDeque.addFirst(2);

        testDeque.removeFirst();
        testDeque.removeLast();

        StdOut.println("size = " + testDeque.size());
        for (Integer integer : testDeque) {
            StdOut.print(integer + " ");
        }
    }

}
