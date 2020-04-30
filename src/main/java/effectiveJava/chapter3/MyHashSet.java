package effectiveJava.chapter3;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author zhouruigang
 * 2019/8/14 14:54
 */
public class MyHashSet<E> extends HashSet<E> {
    private int addCount = 0;

    public MyHashSet() {
    }

    public MyHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        //, int addCount
//        this.addCount = addCount;
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount(){
        return addCount;
    }

    public static void main(String[] args) {

        MyHashSet st = new MyHashSet();
//        st.add("Demo");
        st.addAll(Arrays.asList("AAA","BBB"));

        System.out.println( st.getAddCount());
    }
}
