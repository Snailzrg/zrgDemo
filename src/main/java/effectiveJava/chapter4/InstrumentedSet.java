package effectiveJava.chapter4;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 我们就可以设计具有统计功能的类了，只需要去继承我们刚刚创建的转发类，然后统计的逻辑代码的编写即可：
 *
 *  这样的实现方式，避免了上一节讲的所有问题，由于不使用继承，它不依赖于超类的实现逻辑，也不用担心超类新增新的方法对我们的影响。
 * 而且这样写还有一个好处，这个类可以给所有实现了Set接口的类添加统计功能，而不仅仅是HashSet，还包括TreeSet等其他Set。
 * 其实，这就是装饰模式，InstrumentedSet对Set进行了修饰，给它增加了计数属性。
 * ---------------------
 * 版权声明：本文为CSDN博主「SexyCode」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/hzy38324/article/details/73028781
 * @param <E>
 */
public class InstrumentedSet<E> extends ForwardingSet<E> {
    private int addCount = 0;

    public InstrumentedSet(Set<E> s) {
        super(s);
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

    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        InstrumentedSet<String> s =
                new InstrumentedSet<String>(new HashSet<>());
        s.addAll(Arrays.asList("Snap", "Crackle", "Pop"));
        System.out.println(s.getAddCount());
    }
}
