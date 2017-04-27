package bridge;

/**
 * Created by Administrator on 2017/4/26.
 */
public class MobileMsgFree extends MobileMsgBase {
    @Override
    public void login() {
        super.connect();
    }

    @Override
    public void sendMsg() {
        super.writeText();
    }

    @Override
    public void sendPic() {
        super.drawShape();
    }
}
