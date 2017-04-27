package abstractfactory;

/**扩展新的产品系列 3
 * Created by Administrator on 2017/4/26.
 */
public class Factory3 extends IFactory {
    @Override
    public IProductA createA() {
        return new ProductA3();
    }

    @Override
    public IProductB createB() {
        return new ProductB3();
    }
}
