package bridge.pro;

/**把实现相关的代码抽成MsgImpl基类
 * 此时一些具体的类如MobileMsgFree等也能消除
 * Created by Administrator on 2017/4/26.
 */
public abstract class Msg {

    protected MsgImpl msgImpl;

    public Msg(MsgImpl msgImpl) {
        this.msgImpl = msgImpl;
    }

    // business
    public abstract void login();
    public abstract void sendMsg();
    public abstract void sendPic();

}
