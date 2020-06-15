package Stack;

import java.util.Iterator;

public class StackLL<Item> implements Iterable<Item> {

    private Node head;
    private int size;

    public StackLL() {
        this.head = null;
        this.size = 0;
    }

    public void push(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }
        Node oldHead = head;
        head = new Node();
        head.item = item;
        head.next = oldHead;
        size++;
    }

    public Item pop() {
        Item headItem = head.item;
        head = head.next;
        size--;
        return headItem;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }


    @Override
    public Iterator<Item> iterator() {
        return new StackLLIterator();
    }

    private class StackLLIterator implements Iterator<Item> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove method not supported");
        }
    }

    private class Node {
        Item item;
        Node next;
    }
}
