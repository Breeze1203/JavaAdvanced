package org.pt.design.iterator;

public class Test {
    public static void main(String[] args) {
        ConcreteAggregate aggregate = new ConcreteAggregate(10);

        aggregate.add("Item 1");

        aggregate.add("Item 2");

        aggregate.add("Item 3");

        Iterator iterator = aggregate.createIterator();

        while (iterator.hasNext()) {

            System.out.println(iterator.next());

        }
    }
}
