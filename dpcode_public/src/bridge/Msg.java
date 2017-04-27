package bridge;

/**
 * Created by Administrator on 2017/4/26.
 */
public abstract class Msg {
    // business
    public abstract void login();
    public abstract void sendMsg();
    public abstract void sendPic();

    // impls
    public abstract void playSound();
    public abstract void drawShape();
    public abstract void writeText();
    public abstract void connect();
}
