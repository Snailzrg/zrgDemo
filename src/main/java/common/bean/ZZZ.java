package common.bean;

import java.util.*;

public class ZZZ {
	
	public static void main(String[] args) {
		Map<String, Object> map =new HashMap<>();
		@SuppressWarnings("rawtypes")
		List<Map> ulist =new ArrayList<Map>();
		
		map.put("userId", "userId");
		map.put("name", "name");
		ulist.add(map);
		
		
		List<User> invoices = new ArrayList<User>();
		for (Iterator<Map> localIterator = ulist.iterator();
			 localIterator.hasNext();) {
			Object item = localIterator.next();
			User invoice = (User) item;
			invoices.add(invoice);
		}
		Map resultMap = new HashMap();
		
	}

}
