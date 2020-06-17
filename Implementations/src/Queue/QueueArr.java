package Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueArr<Item> implements Iterable<Item> {

    private int count;
    private Item[] queue;

    public QueueArr() {
        this.count = 0;
        this.queue = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to add cannot be null.");
        }
        if (count > 0 && count == queue.length) {
            resize(2 * queue.length);
        }
        queue[count++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Queue is empty. Cannot dequeue null element");
        }
        if (count > 0 && count == queue.length / 4) {
            resize(queue.length / 2);
        }
        Item item = queue[0];
        for (int i = 1; i < count; i++) {
            queue[i - 1] = queue[i];
        }
        queue[--count] = null;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueArrIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    private class QueueArrIterator implements Iterator<Item> {

        int pointer = 0;

        @Override
        public boolean hasNext() {
            return pointer < queue.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return queue[pointer++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported by queue iterator");
        }
    }

}
