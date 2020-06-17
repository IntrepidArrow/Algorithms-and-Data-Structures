package Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackArr<Item> implements Iterable<Item> {

    private int count;
    private Item[] stack;

    public StackArr() {
        this.count = 0;
        this.stack = (Item[]) new Object[1];
    }

    public void push(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        if (count > 0 && count == stack.length) {
            resize(2 * stack.length);
        }
        stack[count++] = item;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Stack is empty. Cannot pop null element");
        }
        if (count > 0 && count == stack.length / 4) {
            resize(stack.length / 2);
        }
        count--;
        System.out.println(count);
        Item item = stack[count];
        stack[count] = null;
        return item;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        System.out.println("stack.length: "+ stack.length);
        return count;
    }

    @Override
    public Iterator<Item> iterator() {
        return new StackArrIterator();
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            copy[i] = stack[i];
        }
        stack = copy;
    }

    private class StackArrIterator implements Iterator<Item> {

        int pointer = 0;

        @Override
        public boolean hasNext() {
            return pointer < stack.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return stack[pointer++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported by iterator for stack array.");
        }
    }
}
