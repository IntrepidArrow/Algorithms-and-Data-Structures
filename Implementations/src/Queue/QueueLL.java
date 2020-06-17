package Queue;

import java.util.Iterator;

public class QueueLL<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    public QueueLL() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to add cannot be null");
        }
        Node oldTail = tail;
        tail = new Node(item);
        tail.nextNode = null;

        if (isEmpty()) {
            head = tail;
        } else {
            oldTail.nextNode = tail;
        }
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Queue is empty. Cannot dequeue null element");
        }
        Item oldHeadItem = head.item;
        head = head.nextNode;
        size--;
        if (isEmpty()) {
            tail = null;
        }
        return oldHeadItem;
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueLLIterator();
    }

    private class QueueLLIterator implements Iterator<Item> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.nextNode;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported for queue iterator");
        }
    }

    private class Node {
        Item item;
        Node nextNode;

        public Node(Item item) {
            this.item = item;
        }
    }

}
