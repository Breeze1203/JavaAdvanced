package org.pt.design.iterator;

public class ConcreteIterator implements Iterator{
    private ConcreteAggregate aggregate;

    private int position = 0;

    public ConcreteIterator(ConcreteAggregate aggregate) {

        this.aggregate = aggregate;

    }

    @Override

    public boolean hasNext() {

        return position < aggregate.size();

    }

    @Override

    public Object next() {

        if (hasNext()) {
            return aggregate.get(position++);
        }
        return null;

    }
}
