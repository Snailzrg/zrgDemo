//package effectiveJava.chapter5;
//
//import java.io.Serializable;
//
//import org.apache.poi.ss.formula.functions.T;
//
//import zrg.com.bean.Person;
//
//public class FxInterfaceImpl<K extends Person,V> implements FxInterface<K, V>,Cloneable,Serializable {
//
//	private final K[] ks;
//
//	public FxInterfaceImpl(K[] ks, Class<K> keyType, int size) {
//		super();
//		this.ks = ks;
//		this.keyType = keyType;
//		this.size = size;
//	}
//
////	public FxInterfaceImpl() {
////		super();
////		// TODO Auto-generated constructor stub
////	}
//
//
//	private final Class<K> keyType;
//
//
//	private final int size;
//
//	static {
//
//		System.out.println("这是fx-->FxInterfaceImpl的实现");
//
//	}
//
//	/**
//	 * clone 浅拷贝 实现 cloneable
//	 */
//	@Override
//	protected FxInterfaceImpl<K,V> clone() throws CloneNotSupportedException {
//		// TODO Auto-generated method stub
////		return (FxInterfaceImpl<K, V>) super.clone();
//
//		FxInterfaceImpl<K,V> result = null;
//	        try {
//	            result = (FxInterfaceImpl<K, V>) super.clone();
//	        } catch(CloneNotSupportedException e) {
//	            throw new AssertionError();
//	        }
//	        result.ks = result.ks.clone();
//	        result.keyType = keyType;
//	        return result;
//	}
//
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	public T getOne(String id) {
//		// TODO Auto-generated method stub
//
//
//		return null;
//	}
//
//}
