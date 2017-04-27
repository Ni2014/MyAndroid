package decorator.v2;

/**TODO
 * 1 BufferedStream 和 CryptoStream 中有的代码是重复的
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        /*Stream stream1 = new BufferedFileStream();
        stream1 = new NetworkStream();
        stream1 = new CryptoBufferedMemoryStream();*/

        // 转为运行时的组合
        Stream s1 = new BufferedStream(new FileStream());
        s1 = new CryptoStream(new BufferedStream(new NetworkStream()));
        s1 = new BufferedStream(new CryptoStream(new MemoryStream()));

    }
}
