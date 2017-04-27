package proxy;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Proxy extends ISubject {
    private RealSubject realSubject = new RealSubject();
    @Override
    public void req() {
        realSubject.req();
    }
}
