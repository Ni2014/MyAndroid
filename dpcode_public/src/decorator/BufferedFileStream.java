package decorator;

/**
 * 对具体流的扩展 -- 缓存
 * Created by Administrator on 2017/4/26.
 */
public class BufferedFileStream extends FileStream {
    @Override
    public void read() {
        // do buffer
        super.read();
    }

    @Override
    public void seek() {
        // do buffer
        super.seek();
    }

    @Override
    public void write() {
        // do buffer
        super.write();
    }

}
