package singleton;

/**第一次加载时并不会初始化instance 只有第一次调getInstance方法才会初始化instance
 * Created by Administrator on 2017/4/27.
 */
public class SingletonLazyLoad {
    private SingletonLazyLoad() { }


    //lazy load
    private static class SingletonHolder {
        private static final SingletonLazyLoad instance = new SingletonLazyLoad();


    }

    public static SingletonLazyLoad getInstance() {
        return SingletonHolder.instance;
    }
}
