package effectiveJava.chapter6;

/**
 * @author zhouruigang
 *
 */
public class EnumTest {

	public enum Food {
		MILK, APPLE, RICE
	}

	public enum Food2 {
		MILK("牛奶", 100), APPLE("苹果", 50);

		private final String name;
		private final int price;

		Food2(String name, int price) {
			this.name = name;
			this.price = price;
		}
		
		
		
	}

	public static void main(String[] args) {

		System.out.println(Food.APPLE.ordinal() + " " +Food.APPLE.name());
		
		System.out.println(Food2.MILK.price  + Food2.MILK.name);
		
		
		System.out.println(Food2.MILK.compareTo(Food2.APPLE));
	
		
	}

}
