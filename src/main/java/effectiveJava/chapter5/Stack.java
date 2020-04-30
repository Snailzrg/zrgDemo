package effectiveJava.chapter5;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @author zhouruigang
 * 2019/8/16 13:55
 */
public class Stack {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_SIZE=16;

    public Stack(){

        elements =new Object[DEFAULT_SIZE];
    }

    public void push(Object e){
        ensurceSize();
        elements[size++]=e;
    }


    public Object pop(){
       if (size == 0)
            throw new EmptyStackException();
       Object result = elements[--size];
       elements[size] = null;
       return result;
    }

    public  boolean isEmpty(){
        return elements.length==0;
    }

    private void ensurceSize(){
        if (elements.length==size)
            elements= Arrays.copyOf(elements,2*size+1);
    }
}

