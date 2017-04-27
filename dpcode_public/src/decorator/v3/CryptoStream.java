package decorator.v3;

/**
 * Created by Administrator on 2017/4/26.
 */
public class CryptoStream extends MiddleClass {

    public CryptoStream(Stream stream) {
        super(stream);
    }

    @Override
    public void read() {
        // do crypto
        stream.read();
    }

    @Override
    public void seek() {
        // do crypto
        stream.seek();
    }

    @Override
    public void write() {
        // do crypto
        stream.write();
    }
}
