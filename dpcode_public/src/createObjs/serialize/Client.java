package createObjs.serialize;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        User user = new User();
        user.setName("haha");
        user.setAddress("hangzhou");

        SerializationUtil.writeObj(user);
        User userByDeserialize = (User) SerializationUtil.readObj();
        System.out.println(userByDeserialize);
    }
}
