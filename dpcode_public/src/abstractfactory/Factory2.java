package abstractfactory;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Factory2 extends IFactory {
    @Override
    public IProductA createA() {
        return new ProductA2();
    }

    @Override
    public IProductB createB() {
        return new ProductB2();
    }
}
