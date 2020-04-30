package effectiveJava.chapter5;

import org.apache.poi.ss.formula.functions.T;

/**
 * 
 *泛型 
 * 
 * 
 * @author zhouruigang
 *
 * @param <K>
 * @param <V>
 */
public interface FxInterface<K,V> {

	T getOne(String id);
}

