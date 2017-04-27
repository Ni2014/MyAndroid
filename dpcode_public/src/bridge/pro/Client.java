package bridge.pro;

/**
 * decorator 和 bridge 子类数量的减少 -> 亦即复用程度提高了
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
//        Msg msg = new MobileMsgPro();

        // ->
        Msg m1 = new MsgFree(new PCMsgImpl());
        m1 = new MsgPro(new MobileMsgImpl());
    }
}
