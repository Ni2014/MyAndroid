package factorymethod;

/**
 * Created by Administrator on 2017/4/26.
 */
public class FactoryB extends IFactory {
    @Override
    public IProduct create() {
        return new ProductB();
    }
}
