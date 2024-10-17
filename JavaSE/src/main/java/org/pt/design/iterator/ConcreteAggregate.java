package org.pt.design.iterator;

public class ConcreteAggregate implements Aggregate{
    private Object[] items;

    private int size = 0;

    public ConcreteAggregate(int capacity) {

        items = new Object[capacity];

    }

    public void add(Object item) {

        if (size < items.length) {

            items[size++] = item;

        }

    }

    public Object get(int index) {

        return items[index];

    }

    public int size() {

        return size;

    }

    @Override

    public Iterator createIterator() {

        return new ConcreteIterator(this);

    }
}
