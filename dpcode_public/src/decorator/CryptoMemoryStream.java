package decorator;

/**
 * 对具体流的扩展 -- 加密
 * Created by Administrator on 2017/4/26.
 */
public class CryptoMemoryStream extends MemoryStream {
    @Override
    public void read() {
        // do crypto
        super.read();
    }

    @Override
    public void seek() {
        // do crypto
        super.seek();
    }

    @Override
    public void write() {
        // do crypto
        super.write();
    }

}
