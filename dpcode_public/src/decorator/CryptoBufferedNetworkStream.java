package decorator;

/**新的扩展需求 -- 既加密也缓存
 * Created by Administrator on 2017/4/26.
 */
public class CryptoBufferedNetworkStream extends NetworkStream{
    @Override
    public void read() {
        // mix
        super.read();
    }

    @Override
    public void seek() {
        // mix
        super.seek();
    }

    @Override
    public void write() {
        // mix
        super.write();
    }
}
