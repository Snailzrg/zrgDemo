package source.play.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import lombok.Data;

public class Deep {
	
	
	
	private  static void checkAssert(int [] a,int b){
		assert a!=null;
		assert a.length > 0 && b>1;
		System.out.println("123456");
		
	}
	
	private  static void uncheckAssert(int [] a,int b){
		
		System.out.println("654321");
	}
	
	
	
	public static void main(String[] args) {
		
		
		final String[] params = { "summary", "billid", "gid", "ttime",
				"lastTime", "tzgid", "transActor", "loanBal" };
		for (int i = 0; i < params.length; i++) {
		System.out.println(params[i]);
		}
		
		int a[] = {};
		int b = 10;
		System.out.println(a.length);
		checkAssert(null,b);
		uncheckAssert(null,b);
		
		Peson p1=new Peson();
		p1.setAge(10);
		p1.setName("张三");
		
		Peson p2=new Peson();
		p2.setAge(11);
		p2.setName("张三");
		
		
//		System.out.println(Objects.compare(p1, p2, c));
		
		
		System.out.println(p1==p2);
		System.out.println(Objects.equals(p1,p2));
		System.out.println(Objects.deepEquals(p1,p2));
		
		List<Peson> list1 =new ArrayList<Peson>();
		list1.add(p2);
		
		List<Peson> list2=new ArrayList<Peson>();
		list2.add(p1);
		System.out.println(Objects.equals(list1,list2));
		
		Peson pp1=new Peson();
		Peson pp2=new Peson();
		System.out.println(pp1.equals(p2));
	}

}

@Data
class Peson{
	private String name;
	private int age;
	private Hoby hoby;
}

@Data
class Hoby{
	private String name;
	
	private boolean flag =false;
}