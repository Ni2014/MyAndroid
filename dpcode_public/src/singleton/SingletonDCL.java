package singleton;

/**DCL
 * Created by Administrator on 2017/4/27.
 */
public class SingletonDCL {
    private static volatile SingletonDCL instance;
    private SingletonDCL() { }

    public static SingletonDCL getInstance() {
        if (instance == null ) {
            synchronized (SingletonDCL.class) {
                if (instance == null) {
                    instance = new SingletonDCL();
                }
            }
        }
        return instance;
    }
}


/*
1. 先分配内存
2. 执行构造器
3. 将内存地址复制给instance
 */

/*
1. 先分配内存
2. 将内存地址复制给instance
3. 执行构造器
 */