package factorymethod;

/**TODO
 * 如果产品类间是有关系的 比如产品族
 * -> 抽象工厂
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        IFactory factory = new FactoryB();
        IProduct product = factory.create();
        product.demo();
    }
}
