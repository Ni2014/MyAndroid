package abstractfactory;

/**创建1系列的相关产品 A1 B1
 * Created by Administrator on 2017/4/26.
 */
public class Factory1 extends IFactory {
    @Override
    public IProductA createA() {
        return new ProductA1();
    }

    @Override
    public IProductB createB() {
        return new ProductB1();
    }
}
