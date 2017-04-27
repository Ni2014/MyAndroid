package decorator.v2;

/**
 * Created by Administrator on 2017/4/26.
 */
public class BufferedStream extends Stream{
    // 使用者可以new FileStream NetworkStream MemoryStream等 支持变化
    // 继承转组合 此时就能消灭BufferedFileStream BufferedNetworkStream BufferedMemoryStream了 组合使用的CryptoBufferedFileStream等类也能去掉了
    // Crypto相关的 同理

    Stream stream;

    public BufferedStream(Stream stream) {
        this.stream = stream;
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
