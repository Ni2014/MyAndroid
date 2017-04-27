package singleton;

/**懒汉式
 * 问题 即使instance被初始化后 每次调getInstance还是会同步
 * Created by Administrator on 2017/4/27.
 */
public class SingletonLazyer {
    private static volatile SingletonLazyer instance = null;
    private SingletonLazyer() { }

    public static synchronized SingletonLazyer getInstance() {
        if (instance == null) {
            instance = new SingletonLazyer();
        }
        return instance;
    }
}
