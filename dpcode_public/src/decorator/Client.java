package decorator;

/**TODO
 * 1 需要什么样的类就提前写好 不灵活 编译时组装的
 * 2 随着需求的新增 子类的数量会快速膨胀
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        Stream stream1 = new BufferedFileStream();
        stream1 = new NetworkStream();
        stream1 = new CryptoBufferedMemoryStream();

    }
}


// decorator in py
/*
def log(func):
        def wrapper(*args,**kw):
            print 'call %s' % func.__name__
            return func(*args,**kw)
        return wrapper
@log
def now():
        print '20170427'
# same as
now = log(now)
*/

