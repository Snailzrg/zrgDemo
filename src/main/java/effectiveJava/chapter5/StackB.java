package effectiveJava.chapter5;

import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;

/**
 * @author zhouruigang
 * 2019/8/16 13:55
 */
public class StackB<E> {
    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_SIZE = 16;

    public StackB() {
//        elements = new E[DEFAULT_SIZE];
    }

    public void push(E e) {
        ensurceSize();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0)
            throw new EmptyStackException();
        E result = elements[--size];
        elements[size] = null;
        return result;
    }

    public boolean isEmpty() {
        return elements.length == 0;
    }

    public void pushAll(Iterable<? extends E> pushRes){
        for (E e:pushRes ) {
            push(e);
        }
    }

    public void popAll(Collection<? super E> popRes){
        while (!isEmpty())


            popRes.add(pop());
    }


    private void ensurceSize() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }

    public static void main(String[] args) {
    }
}

