package singleton;

/**饿汉式 没线程安全问题
 * Created by Administrator on 2017/4/27.
 */
public class SingletonHungryer {
    private static final SingletonHungryer instance = new SingletonHungryer();

    private SingletonHungryer() {}

    public static SingletonHungryer getInstance() {
        return instance;
    }
}
