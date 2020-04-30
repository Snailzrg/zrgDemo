package other;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Td {


	public static void main(String[] args) {
		
		Map<String, Object> item =new HashMap<>();
		item.put("dxid", 174518617);

//			String ss="{\"jcls\":\"com.ygsoft.grm.travel.pojo.TravelAuditRelaResult\",\"auditMessage\":[\"单据勾稽校验通过。\"],\"auditResult\":\"1\"}";
		String ss = null;
		
if(1==1 && ss.split(",").length>0){

	System.out.println(1);

}
		Integer x =(Integer) item.get("dxid");
		
		System.out.println(x);


	List<TUser> list =new ArrayList<>();
		int i = 5;
		while(i>0){
			TUser user =new TUser();
			user.setAge(i);
			user.setName("name"+i);
			user.setSex(i%2==0?"m":"fm");
			list.add(user);
		}

		System.out.println(list.size());
		
	}
}



@Data
class TUser{
	private   String name;
	private int age;
	private  String sex;
}
