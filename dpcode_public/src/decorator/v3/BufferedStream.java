package decorator.v3;

/**
 * Created by Administrator on 2017/4/26.
 */
public class BufferedStream extends MiddleClass {


    public BufferedStream(Stream stream) {
        super(stream);
    }

    @Override
    public void read() {
        // do buffered
        stream.read();
    }

    @Override
    public void seek() {
        // do buffered
        stream.seek();
    }

    @Override
    public void write() {
        // do buffered
        stream.write();
    }
}
