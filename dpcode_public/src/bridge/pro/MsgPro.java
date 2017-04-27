package bridge.pro;

/**业务的扩展2
 * Created by Administrator on 2017/4/26.
 */
public class MsgPro extends Msg {
    public MsgPro(MsgImpl msgImpl) {
        super(msgImpl);
    }

    @Override
    public void login() {
        msgImpl.playSound();
        msgImpl.connect();
    }

    @Override
    public void sendMsg() {
        msgImpl.playSound();
        msgImpl.writeText();
    }

    @Override
    public void sendPic() {
        msgImpl.playSound();
        msgImpl.drawShape();
    }
}
