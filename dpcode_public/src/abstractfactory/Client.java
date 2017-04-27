package abstractfactory;

/**
 * Created by Administrator on 2017/4/26.
 */
public class Client {
    public static void main(String[] args) {
        IFactory factory = new Factory1();
        IProductA productA = factory.createA();
        IProductB productB = factory.createB();
        productA.demo();
        productB.demo();
        // 扩展
        factory = new Factory3();
        productA = factory.createA();
        productB = factory.createB();
        productA.demo();
        productB.demo();
    }
}
