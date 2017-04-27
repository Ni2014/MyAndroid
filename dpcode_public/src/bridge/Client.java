package bridge;

/**TODO
 * 1 重复的地方
 * 2 原因 Msg包含了 业务相关 和 实现相关的 不同职责(变化的原因)
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        Msg msg = new MobileMsgPro();
    }
}
