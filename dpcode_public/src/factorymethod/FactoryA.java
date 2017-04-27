package factorymethod;

/**1 新增一个产品类时 就新增对应的工厂类 用扩展的方式
 * 2 对IFactory来说 创建具体的产品对象由子类实现
 * Created by Administrator on 2017/4/26.
 */
public class FactoryA extends IFactory {
    @Override
    public IProduct create() {
        return new ProductA();
    }
}
