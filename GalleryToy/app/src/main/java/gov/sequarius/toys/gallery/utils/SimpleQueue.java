package gov.sequarius.toys.gallery.utils;

import java.util.LinkedList;

/**
 * Created by Sequarius on 2016/5/11.
 */
public class SimpleQueue<E> {
    private LinkedList<E> link;


    public SimpleQueue() {
        link = new LinkedList<E>();
    }

    public void add(E obj) {
        link.addFirst(obj);
    }


    public E peek() {
        return link.removeLast();
    }


    public boolean isEmpty() {
        return link.isEmpty();
    }
}
