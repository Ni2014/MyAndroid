package bridge.pro;

/**业务的扩展1
 * Created by Administrator on 2017/4/26.
 */
public class MsgFree extends Msg {
    public MsgFree(MsgImpl msgImpl) {
        super(msgImpl);
    }

    @Override
    public void login() {
        msgImpl.connect();
    }

    @Override
    public void sendMsg() {
        msgImpl.writeText();
    }

    @Override
    public void sendPic() {
        msgImpl.drawShape();
    }
}
