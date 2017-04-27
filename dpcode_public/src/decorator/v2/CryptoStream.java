package decorator.v2;

/**
 * Created by Administrator on 2017/4/26.
 */
public class CryptoStream extends Stream {
    Stream stream;

    public CryptoStream(Stream stream) {
        this.stream = stream;
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
