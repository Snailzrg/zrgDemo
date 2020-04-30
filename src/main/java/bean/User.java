package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouruigang
 * 2019/2/13 9:24
 */

public class User {


//    public enum Test{
//
//        HELLO,
//
//        NO,
//
//        YES,
//
//    }

    private String userId;

    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

//    public static void main(String[] args) {
//
//
//        for (User.Test s : User.Test.values()){
//            System.out.println(s + ", ordinal " + s.ordinal());}
//
////        User user1 = new User("0001", "张三");
////        User user2 = new User("0002", "李松");
////        User user3 = new User("0003", "王虎");
////
////        List<User> users = new ArrayList<>();
////        users.add(user1);
////        users.add(user2);
////        users.add(user3);
////
////        List<User> zuser = users.stream().filter(user -> user.getUserId().equals("000222")).collect(Collectors.toList());
////
////        System.out.println(zuser.size()<1);
////        users.stream().forEach(X->System.out.println(X.name));
////        zuser.stream().forEach(X->System.out.println(X.name));
//
//
//
//  //      User.class.getClassLoader().getResourceAsStream()
//
//        List<String> userIds =new ArrayList<>();
//        userIds.add("111"); userIds.add("222"); userIds.add("333");
//
////
////        String s= string.join(",", userIds.toArray());
////
////        System.out.println(s);
////        StringUtils.join(list, ",");
//
//    }
}
