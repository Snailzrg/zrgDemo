package effectiveJava.chapter4;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * 继承会破坏类的封装性，导致子类非常脆弱，容易受到破坏。
 * 使用复合的方式，对超类进行修饰，使得子类更加的健壮，同时功能更加强大。
 * 	--> 复合模式不适合于回调框架中<--
 *
 * 一个持有Set对象的一个类，这个类实现了Set接口，实现方法里调用了所持有的Set对象的对应的方法，因此我们也叫它转发类：
 * @author zhouruigang
 * 2019/8/14 15:13
 */

public class ForwardingSet<E> implements Set<E> {
    private final Set<E> s;

    public ForwardingSet(Set<E> s) {
        this.s = s;
    }

    public void clear() {
        s.clear();
    }

    public boolean contains(Object o) {
        return s.contains(o);
    }

    public boolean isEmpty() {
        return s.isEmpty();
    }

    public int size() {
        return s.size();
    }

    public Iterator<E> iterator() {
        return s.iterator();
    }

    public boolean add(E e) {
        return s.add(e);
    }

    public boolean remove(Object o) {
        return s.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return s.containsAll(c);
    }

    public boolean addAll(Collection<? extends E> c) {
        return s.addAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return s.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return s.retainAll(c);
    }

    public Object[] toArray() {
        return s.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return s.toArray(a);
    }

    @Override
    public boolean equals(Object o) {
        return s.equals(o);
    }

    @Override
    public int hashCode() {
        return s.hashCode();
    }

    @Override
    public String toString() {
        return s.toString();
    }
}