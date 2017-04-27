package abstractfactory;

/**抽象工厂 A B类产品组成了相关的产品组 且只能是A1B1 A2B2这样的组合 不能出现类似A1B2
 * Created by Administrator on 2017/4/26.
 */
public abstract class IFactory {
    public abstract IProductA createA();
    public abstract IProductB createB();
}
