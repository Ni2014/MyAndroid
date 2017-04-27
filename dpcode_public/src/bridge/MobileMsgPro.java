package bridge;

/**
 * Created by Administrator on 2017/4/26.
 */
public class MobileMsgPro extends MobileMsgBase {
    @Override
    public void login() {
        super.playSound();
        super.connect();
    }

    @Override
    public void sendMsg() {
        super.playSound();
        super.writeText();
    }

    @Override
    public void sendPic() {
        super.playSound();
        super.drawShape();
    }
}
